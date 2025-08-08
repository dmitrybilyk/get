package com.knowledge.get.kotlin

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Thread.sleep

fun main() {
    val stringMono = Mono.just("hello")
    val stringMono2 = Mono.just("hello2")
    val stringsFlux = Flux.fromIterable(listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth"))
    val stringsMono = Mono.just(listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth"))
    val stringsFlux2 = Flux.fromIterable(listOf("Eleventh", "Twelth", "Firting"))
    val polititiansFlux = Flux.just(Polititian(3, "Nme"))


//    Mono.zip(stringMono, stringMono2)
//        .subscribeOn(Schedulers.parallel())
//        .doOnSuccess{
//            println("In Thread: ${Thread.currentThread().name}")
//            println(it)
//        }
//        .subscribe {
//            println("In Thread: ${Thread.currentThread().name}")
//            println(it)
//        }
//
//    Flux.zip(polititiansFlux.repeat(), stringsFlux, stringMono.repeat(), stringMono2.repeat())
//        .map { "${it.t1.id} ${it.t2} ${it.t3} ${it.t4}" }
//    .subscribeOn(Schedulers.immediate())
//    .subscribe {
//        println("in flux In Thread: ${Thread.currentThread().name}")
//        println(it)
//    }
//
//    stringsFlux.mergeWith(stringsFlux2)
//        .publishOn(Schedulers.immediate())
//        .subscribe{ println(it) }
//
//    stringsFlux
//        .map { it.uppercase() }
//        .subscribe { println(it) }

//    val upperSync = stringsFlux.map {
//        println("SYNC map on thread ${Thread.currentThread().name}")
//        it.uppercase()
//    }.subscribeOn(Schedulers.parallel())
//
//    val upperAsync = stringsFlux.flatMap {
//        Mono.just(it.uppercase())
//            .doOnNext { println("ASYNC flatMap on thread ${Thread.currentThread().name}") }
//            .subscribeOn(Schedulers.boundedElastic())
//    }
//
//    upperSync.subscribe { println("SYNC result: $it") }
//    upperAsync.subscribe { println("ASYNC result: $it") }



//    stringsFlux
//        .flatMap { Mono.just(it.uppercase()) }
//        .subscribe { println(it) }
//
//    stringsMono
//        .flatMapMany { Mono.just(it) }
//        .subscribe{ println(it) }

//    Mono.zip(stringMono, stringMono2)
//        .flatMapMany {
//            Flux.just("${it.t1} ${it.t2}", "${it.t2} ${it.t1}")
//        }
//        .subscribe{ println(it) }

    stringsFlux
        .flatMap { str ->
            polititiansFlux.map { pol -> "$str ${pol.name}" }
        }
        .subscribe { println("flatMap politician: $it") }

    Mono.zip(stringMono, stringMono2)
        .flatMapMany { Flux.just(it.t1, it.t2) }
        .collectList()
        .subscribe { println(it) }

    Thread.sleep(1000)

//    sleep(1000)
}

data class Polititian(val id: Long, val name: String)