package knowyourproject.dealservice

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ConcurrentHashMap

data class Person(val id: String, val name: String)

class ReactiveRepo {
    private val db = ConcurrentHashMap<String, Person>()

    fun save(person: Person): Mono<Person> =
        Mono.fromCallable {
            Thread.sleep(100)
            db[person.id] = person
            person
        }.subscribeOn(Schedulers.boundedElastic())

    fun findById(id: String): Mono<Person> =
        Mono.fromCallable<Person> {
            Thread.sleep(80)
            db[id]
        }.subscribeOn(Schedulers.boundedElastic())

    fun deleteById(id: String): Mono<Void> =
        Mono.fromRunnable<Void> {
            Thread.sleep(90)
            db.remove(id)
        }.subscribeOn(Schedulers.boundedElastic())

    fun findAll(): Flux<Person> =
        Flux.defer {
            Thread.sleep(120)
            Flux.fromIterable(db.values)
        }.subscribeOn(Schedulers.boundedElastic())
}

fun main() {
    val repo = ReactiveRepo()
    val start = System.currentTimeMillis()

    Flux.range(1, 100)
        .flatMap({ i ->
            repo.save(Person(i.toString(), "User-$i"))
                .then(repo.findById(i.toString()))
                .doOnNext { println("Found reactive: $it") }
                .then(repo.deleteById(i.toString()))
        }, 50)
        .thenMany(repo.findAll())
        .then(Mono.just("done"))
        .block()   // subscribe & wait for completion

    val duration = System.currentTimeMillis() - start
    println("Reactive test completed in $duration ms")
}

