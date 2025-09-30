package com.knowledge.get.patterns.structural

fun main() {
    val sweetsProducer = ArabicSweetsProducerDecorator(SaltProducerDecorator(SweetsProducerImpl()))
    sweetsProducer.produceCandy()
    sweetsProducer.produceChocolate()
}

interface SweetsProducer {
    fun produceCandy()
    fun produceChocolate()
}

class SweetsProducerImpl:  SweetsProducer {
    override fun produceCandy() {
        println("producing candy")
    }

    override fun produceChocolate() {
        println("producing chocolate")
    }
}

//Decorator
open class BaseSweetsProducerDecorator(private val sweetsProducer: SweetsProducer): SweetsProducer by sweetsProducer

class SaltProducerDecorator(private val sweetsProducer: SweetsProducer): BaseSweetsProducerDecorator(sweetsProducer) {
    override fun produceCandy() {
        println("producing super salt candy")
        super.produceCandy()
    }

    override fun produceChocolate() {
        println("producing super salt chocolate")
        super.produceChocolate()
    }
}

class ArabicSweetsProducerDecorator(private val sweetsProducer: SweetsProducer): BaseSweetsProducerDecorator(sweetsProducer) {
    override fun produceCandy() {
        println("producing super arabic candy")
        super.produceCandy()
    }

    override fun produceChocolate() {
        println("producing super arabic chocolate")
        super.produceChocolate()
    }
}
