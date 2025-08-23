package com.knowledge.get.patterns.creational.builder

fun main() {
    val popupWindow = popupWindow {
        name("some name")
    }

    println(popupWindow)
}

class PopupWindow {
    private var name: String? = null
    private var height: Int? = null
    private var modal: Boolean? = null

    fun name(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "PopupWindow(name=$name, height=$height, modal=$modal)"
    }


}

fun popupWindow(block: PopupWindow.() -> Unit): PopupWindow {
    val popupWindow = PopupWindow()
    popupWindow.block()
    return popupWindow
}