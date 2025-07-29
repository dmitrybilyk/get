package com.knowledge.get.sandbox.repository.reactive

import reactor.core.publisher.Mono
import reactor.core.publisher.Flux

interface ReactiveCrudRepository<T, ID> {
    fun save(entity: T): Mono<T>
    fun findById(id: ID): Mono<T>
    fun findAll(): Flux<T>
    fun delete(id: ID): Mono<Boolean>
}
