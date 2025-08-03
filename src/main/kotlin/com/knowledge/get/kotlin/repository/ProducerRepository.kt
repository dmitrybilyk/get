package com.knowledge.get.kotlin.repository

import com.knowledge.get.kotlin.model.Producer
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ProducerRepository : ReactiveMongoRepository<Producer, String> {
    fun findAllBy(pageable: Pageable): Flux<Producer>
}