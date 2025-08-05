package com.knowledge.get.kotlin.classes.patterns


open class Human(open val name: String, open val age: Int)

data class Man (
    override val name: String, override val age: Int,
    val height: Int, val weight: Int): Human(name, age)

data class Woman (
    override val name: String, override val age: Int,
    val eyesColor: String, val hearColor: String): Human(name, age)

data class Child (
    override val name: String, override val age: Int,
    val numberOfToys: Int): Human(name, age)

class HumanFactory {
    companion object {
        fun createHuman(): Human {
            System.setProperty("human.type", "MAN")
            val humanType = System.getProperty("human.type")
            val type = HumanType.valueOf(humanType)
            return when (type) {
                HumanType.MAN -> Man("Dmytro", 44, 178, 77)
                HumanType.WOMAN -> Woman("Olga", 33, "blue", "black")
                HumanType.CHILD -> Child("Myhaylo", 5, 10)
            }
        }
    }
}

enum class HumanType {
    MAN,
    WOMAN,
    CHILD
}

fun main() {
    val human = HumanFactory.createHuman()
    println(human.name)
}