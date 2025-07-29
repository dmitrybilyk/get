package com.knowledge.get.sandbox.service.reactive

import com.knowledge.get.sandbox.model.Book
import com.knowledge.get.sandbox.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ReactiveBookService(private val repository: ReactiveCrudRepository<Book, String>) :
    ReactiveBaseService<Book, String> {

    override fun create(entity: Book): Mono<Book> =
        repository.save(entity)

    override fun get(id: String): Mono<Book> =
        repository.findById(id)

    override fun getAll(): Flux<Book> =
        repository.findAll()

    override fun delete(id: String): Mono<Boolean> =
        repository.delete(id)
}
