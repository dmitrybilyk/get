package com.knowledge.get.kotlin

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Thread.sleep

data class User(val name: String, val lastName: String)


sealed class Human {
    data class Student(val name: String, val lastName: String) : Human()
    data class Teacher(val name: String, val lastName: String, val salary: Double) : Human()
    object Principal: Human() {
        val car = "Mazda"
    }
}

fun getHumanInfo(human: Human) {
    when (human) {
        is Human.Student -> { println("Student ${human.name}"); }
        is Human.Teacher -> { println("Teacher ${human.name} with salary ${human.salary}"); }
        is Human.Principal -> {
            println("don't know what to say - ${human.car}")
        }
    }
}

fun Item.normalize(): Item = copy(
    status = Status.FIRST,
    price = price.coerceAtLeast(0.0)
)

fun main() {

    val item = Item("Dmytro item", 200.0, Status.FIRST)
        .also {
            println("Original - $it")
            it.status = Status.SECOND
        }

    println(item)

    val newItem = with(item) {
        name = "New name"
        item
    }

    newItem.apply {
        name = "New name2"
        status = Status.THIRD
        price = -20.0
    }

    println(newItem.normalize())

    val item2: Item? = null
    item2.let {
        println("Original - ${it?.status}")
        println("Original2 - $it")
    }



//    val student = Human.Student("Alice", "Smith")
//    val teacher = Human.Teacher("Teacher", "Smith", 1.0)
//    val principal = Human.Principal
//
//    val humanInfo = getHumanInfo(student)


//    val userMono = Mono.just(User("John", "Doe"))
//        .subscribe { println(it) }
//
//    val usersFlux = Flux.just(
//        User("Dmytro", "Bilyk"),
//        User("John", "Doe")
//    ).flatMap {
//        Mono.just(it).map { user -> "${user.name.uppercase()} ${user.lastName}" }
//            .map { str ->
//                val split = str.split(" ")
//                split
//            }
//    }
//        .blockFirst()
////        .subscribe { println(it) }





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

