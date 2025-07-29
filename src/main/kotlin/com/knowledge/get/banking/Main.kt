package com.knowledge.get.banking

import com.knowledge.get.banking.dao.CarDao
import com.knowledge.get.banking.dao.FirmDao
import com.knowledge.get.banking.dao.PersonDao
import kotlinx.coroutines.reactive.collect
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Thread.sleep
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.*
import java.util.function.BiFunction


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

//    Flux.interval(Duration.ofMillis(500)) // Every 500ms a new transaction
//        .map { id -> Transaction(id, (10..300).random()) } // Simulate transaction values
//        .take(20) // Simulate only 20 transactions
//        .doOnNext { println("Received: $it") }
//        .bufferTimeout(5, Duration.ofSeconds(3)) // Batch of 5 or every 3 seconds
//        .filter { it.isNotEmpty() } // Ignore empty batches (shouldn’t happen here)
//        .map { batch ->
//            batch.filter { it.amount > 100 } // Only keep important ones
//        }
//        .filter { it.isNotEmpty() } // Drop batches where nothing is important
//        .flatMap { importantBatch ->
//            saveBatchToDatabase(importantBatch)
//        }
//        .doOnComplete { println("All transactions processed.") }
//        .blockLast()

//    val personsList = listOf(
//        Person("Dmytro", "Kharkiv"),
//        Person("Andriy", "Kyiv"),
//        Person("Ivan", "Lviv"))
//
//    val firmsList = listOf(
//        Firm("Second Firm", "Andriy"),
//        Firm("First Firm", "Dmytro"))
//
//    val carsList = listOf(
//        Car(null, "Andriy"),
//        Car("Fiat", "Dmytro"))
//
//
    val personDao = PersonDao()
    val firmDao = FirmDao()
    val carDao = CarDao()

    Flux.defer {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
        println(now.format(formatter))
        Mono.fromCallable { personDao.getPersons() }
            .doOnNext { persons ->
                println(persons)
                println(Thread.currentThread().name)
            }
            .subscribeOn(Schedulers.parallel())
    }
        .subscribe { println(it) }

    sleep(3000)


//
//    val personsFlux = Flux.defer {
//        Flux.fromIterable(personDao.getPersons())
//    }
////        .subscribeOn(Schedulers.parallel())
//
//    val firmsFlux = Flux.defer {
//        Flux.fromIterable(firmDao.getFirms())
//    }
////        .subscribeOn(Schedulers.parallel())
//
//    val carsFlux = Flux.defer {
//        Flux.fromIterable(carDao.getCars())
//    }
////        .subscribeOn(Schedulers.parallel())
//
//    val firmsMono = firmsFlux.collectMap { it.owner }
//
//    val carsMono = carsFlux.collectMap { it.owner }

//    personsFlux.flatMap { person ->
//        val monoFirm = firmsMono.map { firmMap -> firmMap[person.name] }
//        val monoCar = carsMono.map { carMap -> carMap[person.name] }
//        Mono.zip(monoFirm, monoCar) { firm, car ->
//            PersonSummary(person.name, car!!.name, firm!!.name)
//        }
//    }
//        .subscribeOn(Schedulers.parallel())
//        .subscribe{ println(it) }


//    val result = Flux.defer {
//        val firmsMono = Flux.fromIterable(firmDao.getFirms())
////            .subscribeOn(Schedulers.parallel())
//            .collectMap { it.owner }
//        val carsMono = Flux.fromIterable(carDao.getCars())
////            .subscribeOn(Schedulers.parallel())
//            .collectMap { it.owner }
//        val persons = personDao.getPersons()
//
//        Mono.zip(firmsMono, carsMono)
//            .map { tuple -> tuple.t1 to tuple.t2 }
//            .flatMapMany { (firmMap, carMap) ->
//                Flux.fromIterable(persons)
////                    .subscribeOn(Schedulers.parallel())
//                    .map { person ->
//                    PersonSummary(
//                        person.name,
//                        carMap[person.name]!!.name,
//                        firmMap[person.name]!!.name
//                    )
//                }
//            }
//    }
//        .subscribeOn(Schedulers.parallel())
//
//    result.subscribe { println(it) }
//
//    sleep(10000)
//    {
//        Flux.fromIterable(personsList)
//    }
//        .delayElements(Duration.ofSeconds(1))

//    val firmsFlux = Flux.fromIterable(firmsList)

//    val carsFlux = Flux.fromIterable(carsList)

//    personsFlux.flatMap { person ->
//        val firmsMono = firmsFlux
//            .filter { firm -> firm.owner == person.name }
//            .next()
//
//        firmsFlux
//            .collectMap {  }
//
//        val carsMono = carsFlux
//            .filter { car -> car.owner == person.name }
//            .next()
//            .defaultIfEmpty(Car("Unknown car", person.name))
//
//        Flux.zip(firmsMono, carsMono) { firm, car ->
//            PersonSummary(person.name, car.name, firm.name )
//        }
//
//    }
//        .doOnNext{ println(it) }
//        .subscribe()
//
//    sleep(10000)






//    personsFlux
//        .flatMap { person->
//            val firmMono = findFirmByOwner(firmsFlux, person)
//
//            val carMono = findCarByOwner(carsFlux, person)
//
//            Mono.zip(firmMono, carMono) { firm, car ->
//                PersonSummary(person.name, car.name, firm.name)
//            }
//        }
//        .doOnNext { println(it) }
//        .log()
//        .subscribeOn(Schedulers.parallel())
//        .blockLast()

//
//    val firmsFlux2 = Flux.fromIterable(firmsList)
//
//    Flux.merge(firmsFlux2, firmsFlux)
//        .subscribe{ println(it) }



//    println(" --- Thread name :: " + Thread.currentThread().name)
//
//    Flux.fromIterable(personsList)
//        .flatMap {
//            Mono.just(it)
//                .map { person ->
//                    if (person.name == "Anriy") {
//                        throw RuntimeException("Something went wrong")
//                    }
//                    println(" --- Thread name in map :: " + Thread.currentThread().name)
//                    "${person.name} - ${person.city}"// alias - name of event name+city
//                }
//                .doOnNext{ toPrint -> println(toPrint) }
//                .onErrorResume{ ex -> Mono.just("${ex.message}") }
//                .subscribeOn(Schedulers.parallel())
//        }
//        .subscribe { alias ->
//            println(" --- Thread name in subs :: " + Thread.currentThread().name)
//            println("Received $alias")
//        }
//
//        sleep(10000)
}

private fun findCarByOwner(
    carsFlux: Flux<Car>,
    person: Person
) = carsFlux
    .filter { it.owner.equals(person.name, ignoreCase = true) }
    .next()
    .defaultIfEmpty(Car("Unknown Car", person.name))

private fun findFirmByOwner(
    firmsFlux: Flux<Firm>,
    person: Person
) = firmsFlux
    .filter { it.owner.equals(person.name, ignoreCase = true) }
    .next()
    .defaultIfEmpty(Firm("Unknown Firm", person.name))

data class Transaction(val id: Long, val amount: Int)

fun saveBatchToDatabase(batch: List<Transaction>): Mono<Void> {
    println("💾 Saving to DB: $batch")
    // Simulate non-blocking DB save
    return Mono.delay(Duration.ofMillis(500)).then()
}

data class Person(val name: String, val city: String)

data class Firm(val name: String, val owner: String)

data class Car(val name: String?, val owner: String)

data class PersonSummary(val name: String, val carBrand: String?, val firmName: String)

class Main {

}