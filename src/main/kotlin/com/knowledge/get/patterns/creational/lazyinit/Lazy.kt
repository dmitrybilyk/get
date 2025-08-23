package com.knowledge.get.patterns.creational.lazyinit

class AlertBox {
    var message: String? = null

    fun show() {
        println(" AlertBox $this: $message")
    }
}

class Window {
    private val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        box = AlertBox()
    }
}

fun main() {
    val window = Window()
    window.showMessage("Hi Window")
}