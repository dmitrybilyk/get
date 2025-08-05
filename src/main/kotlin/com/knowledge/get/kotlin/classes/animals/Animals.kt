package com.knowledge.get.kotlin.classes.animals

interface Animal {
    fun speak(): String
}

abstract class Mammal(val name: String): Animal {
    fun info(): String {
        return "I am in mammal named $name"
    }
}

class Dog: Mammal(NAME) {
    override fun speak(): String {
        return "Woof!"
    }

    companion object {
        private const val NAME = "Dog"
    }
}

class Cat constructor(name: String): Mammal(name) {
    val namew = name
    override fun speak(): String {
        return "Meow!"
    }
}

fun main() {
    val animals = listOf(
        Dog(), Cat("Sheva"), Dog()
    )

    animals.forEach { animal ->
        println(animal.info())
        println(animal.speak()) }
}

