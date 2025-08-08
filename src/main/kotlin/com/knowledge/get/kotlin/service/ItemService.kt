package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.exception.ItemNotFoundException
import com.knowledge.get.kotlin.exception.NonPositivePriceException
import com.knowledge.get.kotlin.kafka.ItemKafkaProducer
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.model.ItemWithProducer
import com.knowledge.get.kotlin.model.ProducerItemCount
import com.knowledge.get.kotlin.repository.ItemRepository
import org.bson.types.ObjectId
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val enrichItemService: EnrichItemService,
    private val mongoTemplate: ReactiveMongoTemplate,
    private val producerService: ProducerService,
    private val kafkaProducer: ItemKafkaProducer
) {

    fun findAll(): Flux<Item> = itemRepository.findAll()

    fun findById(id: String): Mono<Item> = itemRepository.findById(id)
        .switchIfEmpty(Mono.error(ItemNotFoundException("Item with id $id not found")))

    fun findByPriceRange(from: Double, to: Double, page: Int): Flux<Item> {
        return Mono.just(from <= to)
            .filter { it }
            .switchIfEmpty(Mono.error(IllegalArgumentException("minPrice must be less than or equal to maxPrice")))
            .flatMapMany {
                val pageable = PageRequest.of(page, 3)
                itemRepository.findByPriceRange(from, to, pageable)
            }
            .doOnNext { println("found: $it") }
            .doOnError { println("error: $it") }
    }

    fun findByManufacturer(name: String): Flux<Item> {
        return itemRepository.findByManufacturerName(name)
    }

    fun findItemWithProducerById(id: String): Mono<ItemWithProducer> {
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("_id").`is`(id)),
            Aggregation.lookup("producers", "producerId", "_id", "producer"),
            Aggregation.unwind("producer")
        )
        return mongoTemplate.aggregate(aggregation, "items", ItemWithProducer::class.java)
            .next()
    }

    fun findAllItemsWithProducers(page: Int, size: Int): Mono<PageImpl<ItemWithProducer>> {
        val skip = page.toLong() * size.toLong()
        val limit = size.toLong()
        val aggregation = Aggregation.newAggregation(
            Aggregation.skip(skip),
            Aggregation.limit(limit),
            Aggregation.lookup("producers", "producerId", "_id", "producer"),
            Aggregation.unwind("producer", true)
        )
        val collectList = mongoTemplate.aggregate(aggregation, "items", ItemWithProducer::class.java)
            .collectList()
        val count = mongoTemplate.count(Query(), "items")

        return Mono.zip(collectList, count).map { tuple ->
            PageImpl(tuple.t1, PageRequest.of(page, size), tuple.t2)
        }
    }

    fun findPlainItems(): Flux<Item> {
        return itemRepository.findAll()
            .take(10)
    }

    fun findWithAggregation(page: Int, size: Int): Mono<PageImpl<ItemWithProducer>> {
        val skip = page.toLong() * size.toLong()
        val limit = size.toLong()
        val criteria = Criteria.where("price").gt(30).lte(2000)
        val query = Query(criteria)
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(criteria),
            Aggregation.skip(skip),
            Aggregation.limit(limit),
            Aggregation.addFields()
                .addFieldWithValue(
                    "discountedPrice",
                    ArithmeticOperators.valueOf("price").multiplyBy(0.1)
                ).build()
        )
        val collectList = mongoTemplate.aggregate(aggregation, "items", ItemWithProducer::class.java)
            .collectList()
        val count = mongoTemplate.count(query, "items")

        return Mono.zip(collectList, count).map { tuple ->
            PageImpl(tuple.t1, PageRequest.of(page, size), tuple.t2)
        }
    }

    fun getProducerItemCount(page: Int, size: Int): Mono<PageImpl<ProducerItemCount>> {
        val skip = page.toLong() * size.toLong()
        val limit = size.toLong()
        val criteria = Criteria.where("price").gt(30).lte(2000)
        val query = Query(criteria)
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("producerId").ne(null)),
            Aggregation.group("producerId")
                .count().`as`("itemCount")
                .avg("price").`as`("avgPrice"),
            Aggregation.sort(Sort.by(Sort.Direction.ASC, "avgPrice")),
            Aggregation.skip(skip),
            Aggregation.limit(limit),
        )
        val collectList = mongoTemplate.aggregate(aggregation, "items", ProducerItemCount::class.java)
            .collectList()
        val count = mongoTemplate.count(query, "items")

        return Mono.zip(collectList, count).map { tuple ->
            PageImpl(tuple.t1, PageRequest.of(page, size), tuple.t2)
        }
    }

    fun save(item: Item): Mono<Item> {
//        return validateProducerExists(item.producerId!!)
//            .then(Mono.just(item))
        return Mono.just(item)
            .flatMap(::validatePrice)
            .map(::normalizeItem)
            .flatMap(::enrichItem)
            .flatMap(::persistItem)
            .doOnNext{
                kafkaProducer.send("item-topic", it)
            }
            .doOnNext{
                kafkaProducer.sendList("items-topic", listOf(it))
            }
            .doOnSubscribe {
                println("Subscribed to save()")
            }
            .doOnSuccess {
                println("Saved successfully: $it")
            }
    }

    private fun validatePrice(item: Item): Mono<Item> {
        return if (item.price > 0.0) {
            Mono.just(item)
        } else {
            Mono.error(NonPositivePriceException("Item price must be positive"))
        }
    }

    private fun validateProducerExists(producerId: ObjectId): Mono<Void> {
        return if (producerId == null) {
            Mono.error(IllegalArgumentException("Producer with id $producerId is null"))
        } else {
            producerService.getProducerById(producerId.toString())
                .switchIfEmpty(Mono.error(IllegalArgumentException("Producer not found")))
                .then()
        }
    }

    private fun normalizeItem(item: Item): Item {
        return item.copy(
            name = item.name?.uppercase(),
            price = item.price.coerceAtLeast(0.0)
        )
    }

    private fun enrichItem(item: Item): Mono<Item> {
        if (item.name == "ENRICH_FAIL") {
            return Mono.error(RuntimeException("Simulated enrichment failure"))
        }

        return enrichItemService.enrichItem(item)
            .timeout(Duration.ofSeconds(1))
            .retry(1)
            .onErrorResume { e ->
                Mono.error(RuntimeException("Failed to enrich item: ${e.message}", e))
            }
    }

    private fun persistItem(item: Item): Mono<Item> {
        if (item.name == "Super Enriched for SAVING_FAIL") {
            return Mono.error(RuntimeException("Simulated DB failure"))
        }

        return itemRepository.save(item)
    }

    fun updateItem(id: String, item: Item): Mono<Item> {
        return itemRepository.findById(id)
            .flatMap { itemRepository.save(item) }
    }

    fun deleteById(id: String): Mono<Void> {
        return itemRepository.deleteById(id)
    }
}