package com.knowledge.get.banking

import org.springframework.data.redis.connection.RedisZSetCommands

fun main() {
    var value: String? = null
    value = "ddd"

    println( value.length.let { it + it }
        .let { "$it fff" }
    )

    when (value) {
        "ddd" -> println("ddddddd")
        "yyy" -> println("yyyyyyy")
    }

    if (value == null) println("null") else println(value)
    println(getLength("ddddddd"))

    val map = mutableMapOf<String, Int>(
        "Dmytro" to 10,
        "Ruslan" to 20
    )

    map.forEach { key, value -> println("$key -> $value") }

    val person = DestructivePerson("Dmytro", 44)

    val (name, _) = person
    println(name)

    val destructivePerson = DestructivePerson("Dmytro", 44)
        .let { "dfdfdf" }
    val list = mutableListOf(
        destructivePerson,
        DestructivePerson("Ruslan", 44),)

    list.forEach{ println(it) }

//    for ((personName, age) in list) {
//        println("$personName - $age")
//    }

    val triple = Triple("Dmytro", 44, "dd")
    val (personName, age, dd) = triple
    val customTuple = CustomTuple(100, "Test", 3.14)

    fun String.firstChar() = this.first()
//    val length: Int get() = this.lentgh

}

data class CustomTuple(val x: Int, val y: String, val z: Double)

data class DestructivePerson(var name: String, val age: Int)

fun getLength(str: String) = str.length

class Kotlin {
}