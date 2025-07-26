package com.knowledge.get.banking

import kotlinx.coroutines.reactive.collect
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.atomic.*


fun main() {
//    val mono = Mono.just("My mono")
//        .map{ it.uppercase() }
//        .doOnNext { println(it) }
//
//    mono.subscribe()
//
//    val flux = Flux.just("One", "Two", "Three")
//        .map{ it.uppercase() }
//        .doOnNext { println(it) }
//
//    flux.subscribe{println("Received $it")}
//
//    Mono.just("some value")
//        .flatMap {
//            Mono.error<String>(RuntimeException("Oops"))
//        }
//        .onErrorResume { e ->
//            println("caught error: ${e.message}")
//            Mono.just("fallback")
//        }
//        .subscribe{println("Received $it")}


//    Flux.just("One", "Two", "Three")
//        .flatMap { word ->
//            Flux.just(word.uppercase())
////                .delayElements(Duration.ofSeconds(1))
//        }
//        .doOnNext{println(it)}
//        .blockFirst()

//    Mono.just("reactive")
//        .map { "${it.uppercase()} world!" }
//        .doOnNext { println(it) }
//        .subscribe()

//    Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//        .filter { n -> n % 2 == 0 }
//        .map { n -> n * 10 }
//        .doOnNext { println(it) }
//        .subscribe()

//    Mono.just(15)
//        .flatMap {
//            if (it < 10) {
//                Mono.error(RuntimeException("Number too small"))
//            } else {
//                Mono.just(it.toString())
//            }
//        }
//        .onErrorResume {Mono.just("Number too small")
//        }
//        .doOnNext { println(it) }
//        .block()

//    val stringsFlux = Flux.just("A", "B", "C")
//    val intsFlux = Flux.just(1, 2, 3)
//
//    Flux.zip(stringsFlux, intsFlux)
//        .flatMap { Mono.just(it)
//            .map { tuple -> tuple.t1 + tuple.t2 }
//            .delayElement(Duration.ofSeconds(1))
//        }
//        .doOnNext { println(it) }
//        .blockLast()

//    val attempts = AtomicInteger(0)
//
//    Mono.defer {
//        val current = attempts.incrementAndGet()
//        println("Attempt $current")
//        if (current < 4) {
//            Mono.error(RuntimeException("Failed on attempt $current"))
//        } else {
//            Mono.just("Success")
//        }
//    }
//        .retry(3)
//        .doOnNext { println("Next attempt: $it") }
//        .doOnError { println("Error: ${it.message}") }
//        .block()

//    val names = Flux.just("Alice", "Bob", "Charlie")
//    val ages = Flux.just(25, 30, 35)
//
//    Flux.zip(names, ages)
//        .flatMap { Mono.just(it)
//            .map { tuple ->
//                "Name is ${tuple.t1} and age is ${tuple.t2} years old"
//            }}
//        .doOnNext { println(it) }
//        .subscribe()

//    Flux.range(1, 10)
//        .flatMap {
//            Mono.just(it)
//                .filter { param -> param % 2 == 0 }
//                .map { value -> value * value }
//                .doOnNext { result -> println(result) }
//        }
//        .subscribe ()
//
//    Flux.range(1, 10)
//        .filter {it % 2 == 0}
//        .map { it * it }
//        .subscribe{println(it)}

//    val words = listOf("hi", "there", "cat", "spring", "web", "flux")
//
//    Flux.fromIterable(words)
//        .flatMap { Mono.just(it)
//            .map { toUppercase -> toUppercase.uppercase() }
//            .delayElement(Duration.ofMillis(500))
//        }
//        .filter { toFilter -> toFilter.length >= 4 }
//        .collectList()
//        .doOnNext { println(it) }
//        .block()

//    val names = listOf("Alice", "Bob", "Charlie")
//    val scores = listOf(85, 92, 78)
//
//    val namesFlux = Flux.fromIterable(names)
//    val scoresFlux = Flux.fromIterable(scores)
//
//    Flux.zip(namesFlux, scoresFlux)
//        .flatMap { Mono.just(it)
//            .map { tuple -> "${tuple.t1} scored ${tuple.t2}" }
//        }
//        .delayElements(Duration.ofMillis(500))
//        .doOnNext { println(it) }
//        .blockLast()

//    val people = listOf(
//        Person("Alice", "New York"),
//        Person("Bob", "Paris"),
//        Person("Charlie", "New York"),
//        Person("David", "Paris"),
//        Person("Eve", "London")
//    )
//
//    Flux.fromIterable(people)
//        .groupBy {
//            it.city
//        }
////        .flatMap { groupedFlux ->
////            groupedFlux
////                .map { it.name }
////                .collectList()
////                .map {namesList ->
////                    "City: ${groupedFlux.key()} -> $namesList"
////                }
////        }
//        .doOnNext { println(it) }
//        .blockLast()

//    val fluxNumbers = Flux.interval(Duration.ofMillis(300))
//        .take(5)
//        .buffer(Duration.ofMillis(1000))
//        .doOnNext { println(it) }
//        .blockLast(Duration.ofSeconds(1))

    Flux.interval(Duration.ofMillis(500)) // Every 500ms a new transaction
        .map { id -> Transaction(id, (10..300).random()) } // Simulate transaction values
        .take(20) // Simulate only 20 transactions
        .doOnNext { println("Received: $it") }
        .bufferTimeout(5, Duration.ofSeconds(3)) // Batch of 5 or every 3 seconds
        .filter { it.isNotEmpty() } // Ignore empty batches (shouldn’t happen here)
        .map { batch ->
            batch.filter { it.amount > 100 } // Only keep important ones
        }
        .filter { it.isNotEmpty() } // Drop batches where nothing is important
        .flatMap { importantBatch ->
            saveBatchToDatabase(importantBatch)
        }
        .doOnComplete { println("All transactions processed.") }
        .blockLast()


}

data class Transaction(val id: Long, val amount: Int)

fun saveBatchToDatabase(batch: List<Transaction>): Mono<Void> {
    println("💾 Saving to DB: $batch")
    // Simulate non-blocking DB save
    return Mono.delay(Duration.ofMillis(500)).then()
}

data class Person(val name: String, val city: String)

class Main {

}