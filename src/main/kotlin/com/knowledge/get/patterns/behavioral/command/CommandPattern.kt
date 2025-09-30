package com.knowledge.get.patterns.behavioral.command

import java.lang.Thread.sleep

fun main() {
    val acceptOrderCommand = AcceptOrderCommand(2, 5)
    val payOrderCommand = PayOrderCommand(4, 34.2)

    val orderProcessor = OrderProcessor()
    orderProcessor.addOrder(acceptOrderCommand)
    orderProcessor.addOrder(payOrderCommand)

    sleep(3000)

    orderProcessor.processOrders()
}

interface Command {
    fun execute()
}

class AcceptOrderCommand(private val orderId: Int, private val quantity: Int): Command {
    override fun execute() {
        println("Ordering order with Id $orderId and quantity is $quantity")
    }
}

class PayOrderCommand(private val orderId: Int, private val amount: Double): Command {
    override fun execute() {
        println("Paying order with Id $orderId and sum is $amount")
    }
}

class OrderProcessor {
    val orderCommands = mutableListOf<Command>()

    fun addOrder(command: Command) {
        orderCommands.add(command)
    }

    fun processOrders() {
        orderCommands.forEach { it.execute() }
    }
}