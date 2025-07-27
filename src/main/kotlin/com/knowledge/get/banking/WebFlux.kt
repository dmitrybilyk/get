package com.knowledge.get.banking

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

fun main() {
    val mono = Mono.just(5)
        .flatMap {
            println(" -- In flatmap <<< ${Thread.currentThread().name} >>> ")
            Mono.just(it * 3)
        }
        .doOnNext {
            println(" -- In doonnext <<< ${Thread.currentThread().name} >>> ")
            println(it)
        }
        .subscribeOn(Schedulers.parallel())

    mono.subscribe()




    val flux = Flux.just(1, 2, 3)
        .concatMap { id ->
            Flux.just("Request for $id")
                .delayElements(Duration.ofMillis(100))
        }
        .doOnNext { println(it) }
        .subscribe()

    Thread.sleep(2000)
}

class WebFlux {
}