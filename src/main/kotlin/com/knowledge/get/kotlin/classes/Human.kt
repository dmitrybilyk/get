package com.knowledge.get.kotlin.classes

data class Human (val name: String, val age: Int, val address: String?) {
}

fun main() {
    val human = Human("Dmytro", 33, "home")

    println(human.address.let { it?.length!! > 2 })

    println( listOf(
        Human("first", 33, "home1"),
        Human("first2", 10, "home0"),
        Human("second", 33, "home2"))
        .associateBy (
            { it.name },
            { it.address }
        )
    ).let(::println)
//        .filter { it.age > 30 }
//        .map { it.address } )
}