package com.knowledge.get.kotlin

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

data class User(val name: String, val lastName: String)

fun main() {

    val userMono = Mono.just(User("John", "Doe"))
        .subscribe { println(it) }

    val usersFlux = Flux.just(
        User("Dmytro", "Bilyk"),
        User("John", "Doe")
    ).flatMap {
        Mono.just(it).map { user -> "${user.name.uppercase()} ${user.lastName}" }
            .map { str ->
                val split = str.split(" ")
                split
            }
    }
        .blockFirst()
//        .subscribe { println(it) }





//    val namesFlux = Flux.just( "Dmytro", "Dmytro and Andrii" )
//    val lastNamesFlux = Flux.just( "Bilyk", "Bilyk and Gorbatov" )
//
////    Flux.zip(namesFlux, lastNamesFlux) { name, lastName -> "$name - $lastName" }
////        .subscribe{ println(it) }
//    Flux.zip(namesFlux, lastNamesFlux) { name, lastName -> User(name, lastName) }
//        .subscribe{ println(it) }


//    Flux.just(User("2", "4"), User("3", "4"))
//        .subscribe { println(it) }



//    val resultFlux: Flux<Tuple2<String, String>> = namesFlux.zipWith(lastNamesFlux)

//        .subscribe{ println(it) }
//        namesFlux
//        .all { it.contains("Dmytro") }
//        .subscribe { println("I'm ready! $it") }


//
//    val item = Item("ddddd", 4.55, Status.THIRD)
//
//    val (name, age) = item
//
//    println(name)
//
//    println(Status.FIRST.name)
//
//    var items = listOf(
//        Item("fff", 3.44, Status.FIRST),
//        Item("fff", 3.44, Status.FIRST)
//    )
//
//    val map = mutableMapOf("Dmytro" to 33, "Ruslan" to 44)
//    map["Dmytro"] = 44

//    useItems(items)

}

open class ItemParent {
    var parentProperty: String = ""
}

data class Item(var name: String, var price: Double, var status: Status) : ItemParent()

enum class MyEnum {
    STARTED {
        override fun signal() = PAUSED
    },
    STOPPED {
        override fun signal() = PAUSED
    },
    PAUSED {
        override fun signal() = PAUSED
    };

    abstract fun signal(): MyEnum
}

enum class Status {
    FIRST,
    SECOND,
    THIRD
}

