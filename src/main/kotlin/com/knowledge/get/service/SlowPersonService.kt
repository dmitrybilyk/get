package com.knowledge.get.service

import com.knowledge.get.model.Person
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class SlowPersonService {

    fun getPersonById(id: String): Mono<Person> {
        println("Fetching person $id on thread: ${Thread.currentThread().name}")
        return Mono.just(Person(id, "User $id", "user$id@example.com", 30))
            .delayElement(Duration.ofSeconds(2)) // ⏱️ Simulate slow DB
            .doOnNext { println("Done fetching $id on thread: ${Thread.currentThread().name}") }
    }

    fun enrichPerson(person: Person): Mono<Person> {
        println("Enriching person ${person.id} on thread: ${Thread.currentThread().name}")
        return Mono.just(person.copy(name = person.name + " ✅ enriched"))
            .delayElement(Duration.ofSeconds(2)) // ⏱️ Simulate slow API
            .doOnNext { println("Done enriching ${person.id} on thread: ${Thread.currentThread().name}") }
    }

    fun enhancePerson(person: Person): Mono<Person> {
        println("Enhancing person ${person.id} on thread: ${Thread.currentThread().name}")
        return Mono.just(person.copy(name = person.name + " enhanced"))
            .delayElement(Duration.ofSeconds(2))
            .doOnNext {println(" Done enhancing ${person.id} on thread ${Thread.currentThread().name}")}
    }

    fun getEnriched(id: String): Mono<Person> =
        getPersonById(id)
            .flatMap { enrichPerson(it) }
//            .flatMap { enhancePerson(it) }
}
