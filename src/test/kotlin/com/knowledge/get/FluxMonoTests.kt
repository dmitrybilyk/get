package com.knowledge.get

import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import reactor.util.function.Tuple2
import reactor.util.function.Tuples
import java.lang.Thread.sleep
import java.time.Duration
import java.util.concurrent.*
import java.util.function.BiFunction
import java.util.function.Consumer

class FluxMonoTests {


    @Test
    fun testFlux(){
        val flux = Flux.just("A", "B", "C");
        StepVerifier.create(flux)
            .expectNext("A", "B", "C")
            .verifyComplete()
    }

    @Test
    fun testFluxMap(){
        val flux = Flux.just("Abbb Ddd", "Bbbb Kkkk", "Cccc Bddd")
            .map {
                val list = it.split(" ")
                Player(list[0], list[1])
            }

        StepVerifier.create(flux)
            .expectNext(Player("Abbb", "Ddd"))
            .expectNext(Player("Bbbb", "Kkkk"))
            .expectNext(Player("Cccc", "Bddd"))
            .verifyComplete()
    }

    @Test
    fun testFluxMerge() {
        val first = Flux.just("One", "Two", "Three")
            .delayElements(Duration.ofSeconds(1))
        val second = Flux.just("OneIs", "TwoIs", "ThreeIs")

        val result = Flux.merge(first, second)

        StepVerifier.create(result)
            .expectNext("OneIs")
            .expectNext("TwoIs")
            .expectNext("ThreeIs")
            .expectNext("One")
            .expectNext("Two")
            .expectNext("Three")
            .verifyComplete()
    }

    @Test
    fun testFluxZip() {
        val first = Flux.just("One", "Two", "Three")
            .delayElements(Duration.ofSeconds(1))
        val second = Flux.just("OneIs", "TwoIs", "ThreeIs")

        val result = Flux.zip(first, second)

        StepVerifier.create(result)
            .expectNext(Tuples.of("One", "OneIs"))
            .expectNext(Tuples.of("Two", "TwoIs"))
            .expectNext(Tuples.of("Three", "ThreeIs"))
            .verifyComplete()
    }

    @Test
    fun testFluxFilter() {
        val result = Flux.just("One", "Two", "Three")
            .filter { it != "One" }


        StepVerifier.create(result)
            .expectNext("Two")
            .expectNext("Three")
            .verifyComplete()
    }

    @Test
    fun testFluxFromArray() {
        val result = Flux.fromIterable(listOf("One", "Two", "Three"))
            .filter { it != "One" }


        StepVerifier.create(result)
            .expectNext("Two")
            .expectNext("Three")
            .verifyComplete()
    }

    @Test
    fun testFluxSubscibe() {
        val result = Flux.just("One", "Two", "Three")
            .filter { it != "One" }
//            .delayElements(Duration.ofMillis(500))
            .subscribe{ object: Flow.Subscriber<String> {
                override fun onNext(item: String?) {
                    println("dddddddddddddddddd" + it[0])
                }

                override fun onSubscribe(subscription: Flow.Subscription?) {
                    subscription!!.request(Long.MAX_VALUE)
                }

                override fun onError(throwable: Throwable?) {
                    TODO("Not yet implemented")
                }

                override fun onComplete() {
                    TODO("Not yet implemented")
                }
            }            }

        sleep(Duration.ofSeconds(1))

//        StepVerifier.create(result)
//            .expectNext("Two")
//            .expectNext("Three")
//            .verifyComplete()
    }

    @Test
    fun testFluxZipToPlayer() {
        val names = Flux.just("Dmytro", "Andrii")
        val lastNames = Flux.just("Bilyk", "Gorbatov")
        val zipped = Flux.zip(names, lastNames) { name, lastName -> "$name $lastName" }

        StepVerifier.create(zipped)
            .expectNext("Dmytro Bilyk")
            .expectNext("Andrii Gorbatov")
            .verifyComplete()
    }


    @Test
    fun testFlatMapKnowledge() {
        val playersFlux = Flux.just("Dmytro Bilyk", "Andrii Gorbatov")
            .flatMap { name ->
                Mono.just(name)
                    .map {
                        val splitList = it.split(" ")
                        Player(splitList[0], splitList[1])
                    }
            }
//            .subscribeOn(Schedulers.parallel())


        val expectedList = listOf(
            Player("Dmytro", "Bilyk"),
            Player("Andrii", "Gorbatov")
        )

        StepVerifier.create(playersFlux)
            .expectNextMatches{expectedList.contains(it)}
            .expectNextMatches{expectedList.contains(it)}
            .verifyComplete()
            }



//    @Test
//    fun flatMapTest() {
//        val playerFlux = Flux
//            .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
//            .flatMap { name ->
//                Mono.just(name)
//                    .map { fullName ->
//                        val split = fullName.split(" ")
//                        Player(split[0], split[1])
//                    }
//                    .subscribeOn(Schedulers.parallel())
//            }
//
//        val expectedPlayers = listOf(
//            Player("Michael", "Jordan"),
//            Player("Scottie", "Pippen"),
//            Player("Steve", "Kerr")
//        )
//
//        StepVerifier.create(playerFlux)
//            .expectNextMatches { expectedPlayers.contains(it) }
//            .expectNextMatches { expectedPlayers.contains(it) }
//            .expectNextMatches { expectedPlayers.contains(it) }
//            .verifyComplete()
//    }

    @Test
    fun testLogicalOperations() {
        val namesFlux = Flux.just("One", "Two")
        val monoBoolean = namesFlux.all { it.contains("O", true) }

        StepVerifier.create(monoBoolean)
            .expectNext(true)
            .verifyComplete()
    }


}

data class Player(val name:String, val surname: String)