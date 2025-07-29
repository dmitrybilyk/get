package com.knowledge.get.kotlin.service

import com.knowledge.get.kotlin.exception.ItemNotFoundException
//import com.knowledge.get.kotlin.kafka.ItemKafkaProducer
import com.knowledge.get.kotlin.model.Item
import com.knowledge.get.kotlin.repository.ItemRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ItemService (
    private val repository: ItemRepository,
    private val enrichItemService: EnrichItemService,
//    private val kafkaProducer: ItemKafkaProducer
) {

    fun save(item: Item): Mono<Item> {
        return Mono.just(item)
            .filter{ it.name.isNotBlank() && it.price > 0.0 }
            .switchIfEmpty( Mono.error(IllegalArgumentException("Item name must not be empty and price must be positive")) )
            .map {
                it.copy(
                    id = it.id,
                    name = it.name.uppercase(),
                    price = it.price.coerceAtLeast(0.0)
                )
            }
            .flatMap { enrichItemService.enrichItem(it)
                .timeout(Duration.ofSeconds(1))
                .retry(1)
                .onErrorResume { e ->
                    Mono.error(RuntimeException("Failed to enrich item: ${e.message}", e))
                }}
            .flatMap { repository.save(it)
                .also { println("About to save item: $it") }}
//            .doOnNext {
//                kafkaProducer.send("item-topic", it)
//            }
            .doOnSuccess{ it.also { println("Saved successfully: $it") } }
            .doOnSubscribe {
                println("subscribed")
            }
    }


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
}