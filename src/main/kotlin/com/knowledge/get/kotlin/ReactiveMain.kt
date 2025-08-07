package com.knowledge.get.kotlin

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.lang.Thread.sleep

fun main() {

    val startTime = System.currentTimeMillis()

    val dbMono = Mono.defer { Mono.fromCallable { getValueFromDb() } }
        .subscribeOn(Schedulers.parallel())

    val serviceMono = Mono.defer { Mono.fromCallable { getValueExternalService() } }
        .subscribeOn(Schedulers.parallel())

    val itemsFromServiceFlux = Mono.defer { Mono.fromCallable { getValuesExternalService() } }
        .flatMapMany { Flux.fromIterable(it) }
        .subscribeOn(Schedulers.parallel())

    itemsFromServiceFlux
        .flatMap { Mono.zip(
            dbMono, serviceMono, Mono.just(it)
        ) }
        .doOnNext { println(it) }
        .doOnTerminate {
            val duration = System.currentTimeMillis() - startTime
            println("Total duration: $duration ms")
        }
        .subscribe()

//    Flux.zip(dbMono.repeat(), serviceMono.repeat(), itemsFromServiceFlux)
//        .doOnNext { println(it) }
////        .doOnNext { println("${it.t1} - ${it.t2}") }
//        .doOnTerminate{
//            val duration = System.currentTimeMillis() - startTime
//            println(duration)
//        }
//        .subscribe()

    sleep(18000)

}

fun getValueFromDb(): String {
    sleep(3000)
    println("${Thread.currentThread().name} - in getValueFromDb")
    return "Value from DB"
}

fun getValueExternalService(): String {
    sleep(2000)
    println("${Thread.currentThread().name} - in getValueExternalService")
    return "Value from Service"
}

fun getValuesExternalService(): List<String> {
    sleep(2000)
    println("${Thread.currentThread().name} - in getValuesExternalService")
    return listOf("First", "Second", "Third")
}