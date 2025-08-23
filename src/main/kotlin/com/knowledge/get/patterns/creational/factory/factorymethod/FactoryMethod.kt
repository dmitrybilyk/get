package com.knowledge.get.patterns.creational.factory.factorymethod

fun main() {
    val family = GoodFamily()
    println(family.createMember())
}

abstract class Family {
    abstract fun createMember(): Human
}

class GoodFamily: Family() {
    override fun createMember(): Human {
        return GoodHuman()
    }
}

class BadFamily: Family() {
    override fun createMember(): Human {
        return BadHuman()
    }
}

abstract class Human

class GoodHuman: Human()
class BadHuman: Human()