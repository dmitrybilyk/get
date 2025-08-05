package com.knowledge.get.kotlin.classes.generics

fun main() {
    val box = Box(SomeClass("Some value"))
    println(box.value)
}


class Box<T>(val value: T)

data class SomeClass(val someValue: String)

interface GenericInterface<T> {
    fun returnValue(): T
}

class GenericClass: GenericInterface<String> {
    override fun returnValue(): String {
        return "dfdfd"
    }
}

class GenericIntClass: GenericInterface<Int> {
    override fun returnValue(): Int {
        return 77
    }
}

class Producer<out T>(private val value: T) {
    fun get(): T = value
}

val strProducer: Producer<String> = Producer("Hello")
val anyProducer: Producer<Any> = strProducer