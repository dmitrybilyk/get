package com.knowledge.get.kotlin.repository

import com.knowledge.get.kotlin.model.Item
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ItemRepository : ReactiveMongoRepository<Item, String> {
    fun findByPriceBetweenOrderByPriceAsc(from: Double, to: Double): Flux<Item>

    @Query(" {'price': {\$gte:  ?0, \$lte:  ?1 } }")
    fun findByPriceRange(from: Double, to: Double, pageable: Pageable): Flux<Item>
}