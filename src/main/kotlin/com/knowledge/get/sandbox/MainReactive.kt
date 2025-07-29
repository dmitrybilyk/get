package com.knowledge.get.sandbox

import com.knowledge.get.sandbox.model.Book
import com.knowledge.get.sandbox.repository.reactive.InMemoryReactiveRepository
import com.knowledge.get.sandbox.service.reactive.ReactiveBookService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Thread.sleep
import java.time.Duration

fun main() {
    val bookRepo = InMemoryReactiveRepository<Book, String> { it.id }
    val bookService = ReactiveBookService(bookRepo)

    val booksFlux = Flux.just(
        Book("1", "Kotlin in Action", "Dmitry Jemerov"),
        Book("2", "Effective Kotlin", "Marcin Moskala")
    )

    booksFlux
        .flatMap { bookService.create(it) }
        .doOnNext { println("Saved: $it") }
        .thenMany(bookService.getAll())
        .doOnNext { println("Final: $it") }
        .blockLast()
}