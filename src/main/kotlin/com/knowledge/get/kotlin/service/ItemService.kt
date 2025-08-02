package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.exception.ItemNotFoundException
import com.knowledge.get.kotlin.exception.NonPositivePriceException
//import com.knowledge.get.kotlin.kafka.ItemKafkaProducer
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.model.ItemWithProducer
import com.knowledge.get.kotlin.repository.ItemRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ItemService (
    private val repository: ItemRepository,
    private val enrichItemService: EnrichItemService,
    private val mongoTemplate: ReactiveMongoTemplate
//    private val kafkaProducer: ItemKafkaProducer
) {

    fun findAll(): Flux<Item> = repository.findAll()

    fun findById(id: String): Mono<Item> = repository.findById(id)
        .switchIfEmpty(Mono.error(ItemNotFoundException("Item with id $id not found")))

    fun findByPriceRange(from: Double, to: Double, page: Int): Flux<Item> {
        return Mono.just(from <= to)
            .filter { it }
            .switchIfEmpty(Mono.error(IllegalArgumentException("minPrice must be less than or equal to maxPrice")))
            .flatMapMany {
                val pageable = PageRequest.of(page, 3)
                repository.findByPriceRange(from, to, pageable)
            }
            .doOnNext { println("found: $it") }
            .doOnError { println("error: $it") }
    }

    fun findByManufacturer(name: String): Flux<Item> {
        return repository.findByManufacturerName(name)
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
            Aggregation.unwind("producer")
        )
        val collectList = mongoTemplate.aggregate(aggregation, "items", ItemWithProducer::class.java)
            .collectList()
        val count = mongoTemplate.count(Query(), "items")

        return Mono.zip(collectList, count).map { tuple ->
            PageImpl(tuple.t1, PageRequest.of(page, size), tuple.t2)
        }
    }

    fun save(item: Item): Mono<Item> {
        return Mono.just(item)
            .flatMap(::validatePrice)
            .map(::normalizeItem)
            .flatMap(::enrichItem)
            .flatMap(::persistItem)
            .doOnSubscribe { println("Subscribed to save()") }
            .doOnSuccess { println("Saved successfully: $it") }
    }

    private fun validatePrice(item: Item): Mono<Item> {
        return if (item.price > 0.0) {
            Mono.just(item)
        } else {
            Mono.error(NonPositivePriceException("Item price must be positive"))
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

        return repository.save(item)
    }
}