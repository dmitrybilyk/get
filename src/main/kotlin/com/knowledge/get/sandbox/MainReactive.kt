package com.knowledge.get.sandbox

import com.knowledge.get.sandbox.model.Book
import com.knowledge.get.sandbox.repository.reactive.InMemoryReactiveRepository
import com.knowledge.get.sandbox.service.reactive.ReactiveBookService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Thread.sleep

fun main() {
    val bookRepo = InMemoryReactiveRepository<Book, String> { it.id }
    val bookService = ReactiveBookService(bookRepo)

    val booksFlux = Flux.just(
        Book("1", "Kotlin in Action", "Dmitry Jemerov"),
        Book("2", "Effective Kotlin", "Marcin Moskala")
    )

    val books = listOf(
        Book("1", "Kotlin in Action", "Dmitry Jemerov"),
        Book("2", "Effective Kotlin", "Marcin Moskala")
    )

    booksFlux.flatMap {
        bookService.create(it)
            .subscribeOn(Schedulers.parallel())
    }
//        .subscribeOn(Schedulers.parallel())
        .subscribe { println(it) }

    sleep(7000)

//    booksFlux.{ book ->
//        bookService.create(book)
//            .doOnNext {
//                println("In thread ${Thread.currentThread().name}")
//                println(it)
//            }
//            .subscribe { println(it) }
//    }



//    booksFlux
//        .flatMap { bookService.create(it) }

}