package com.knowledge.get.sandbox.service.reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactiveBaseService<T, ID> {
    fun create(entity: T): Mono<T>
    fun get(id: ID): Mono<T>
    fun getAll(): Flux<T>
    fun delete(id: ID): Mono<Boolean>
}
