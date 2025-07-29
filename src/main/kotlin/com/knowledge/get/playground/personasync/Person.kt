package com.knowledge.get.playground.personasync

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

data class Person(val name: String, val city: String)
data class Firm(val name: String, val owner: String)
data class Car(val name: String?, val owner: String)
data class PersonSummary(val person: String, val car: String, val firm: String)

interface PersonDao {
    fun getPersons(): Flux<Person>
}

interface FirmDao {
    fun getFirms(): Flux<Firm>
}

interface CarDao {
    fun getCars(): Flux<Car>
}

class PersonDaoImpl : PersonDao {
    override fun getPersons(): Flux<Person> {
        val persons = listOf(
            Person("Dmytro", "Kharkiv"),
            Person("Andriy", "Kyiv"),
            Person("Ivan", "Lviv")
        )
        return Flux.fromIterable(persons)
    }
}

class FirmDaoImpl : FirmDao {
    override fun getFirms(): Flux<Firm> {
        val firms = listOf(
            Firm("Second Firm", "Andriy"),
            Firm("First Firm", "Dmytro")
        )
        return Flux.fromIterable(firms)
    }
}

class CarDaoImpl : CarDao {
    override fun getCars(): Flux<Car> {
        val cars = listOf(
            Car(null, "Andriy"),
            Car("Fiat", "Dmytro")
        )
        return Flux.fromIterable(cars)
    }
}

fun main() {
    val personDao: PersonDao = PersonDaoImpl()
    val firmDao: FirmDao = FirmDaoImpl()
    val carDao: CarDao = CarDaoImpl()

    val result = Flux.defer {
        println("[Main] Starting defer block on thread: ${Thread.currentThread().name}")

        val firmsMono = firmDao.getFirms()
            .doOnSubscribe { println("[DAO] Subscribed to getFirms on thread: ${Thread.currentThread().name}") }
            .doOnNext { println("[DAO] Emitting firm: ${it.name} on thread: ${Thread.currentThread().name}") }
            .subscribeOn(Schedulers.boundedElastic())
            .collectMap { it.owner }

        val carsMono = carDao.getCars()
            .doOnSubscribe { println("[DAO] Subscribed to getCars on thread: ${Thread.currentThread().name}") }
            .doOnNext { println("[DAO] Emitting car: ${it.name} on thread: ${Thread.currentThread().name}") }
            .subscribeOn(Schedulers.boundedElastic())
            .collectMap { it.owner }

        val personsFlux = personDao.getPersons()
            .doOnSubscribe { println("[DAO] Subscribed to getPersons on thread: ${Thread.currentThread().name}") }
            .doOnNext { println("[DAO] Emitting person: ${it.name} on thread: ${Thread.currentThread().name}") }
            .subscribeOn(Schedulers.parallel())

        Mono.zip(firmsMono, carsMono)
            .flatMapMany { tuple ->
                val firmMap = tuple.t1
                val carMap = tuple.t2
                println("[Zip] Maps ready on thread: ${Thread.currentThread().name}")

                personsFlux.map { person ->
                    val car = carMap[person.name]
                    val firm = firmMap[person.name]
                    println("[Summary] Processing ${person.name} on thread: ${Thread.currentThread().name}")
                    PersonSummary(
                        person.name,
                        car?.name ?: "NoCar",
                        firm?.name ?: "NoFirm"
                    )
                }
            }
    }.subscribeOn(Schedulers.boundedElastic())

    result
        .doOnNext { println("[Result] Emitted summary: $it on thread: ${Thread.currentThread().name}") }
        .blockLast()
}
