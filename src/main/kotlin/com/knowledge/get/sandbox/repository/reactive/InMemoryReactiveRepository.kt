package com.knowledge.get.sandbox.repository.reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Thread.sleep

class InMemoryReactiveRepository<T: Any, ID>(
    private val idSelector: (T) -> ID
) : ReactiveCrudRepository<T, ID> {

    private val store = mutableMapOf<ID, T>()

    override fun save(entity: T): Mono<T> {
        println("Before Saving $entity, time - ${System.currentTimeMillis()}")
        return Mono.fromCallable {
            sleep(3000)
            println("Saving entity $entity in repository save. Thread >>> ${Thread.currentThread().name}, time - ${System.currentTimeMillis()}")
            val id = idSelector(entity)
            store[id] = entity
            entity
        }
    }


    override fun findById(id: ID): Mono<T> =
        Mono.justOrEmpty(store[id])

    override fun findAll(): Flux<T> {
        println("Getting all in repo. Thread >>> ${Thread.currentThread().name}")
        return Flux.fromIterable(store.values.toList())
    }


    override fun delete(id: ID): Mono<Boolean> =
        Mono.fromCallable { store.remove(id) != null }
}