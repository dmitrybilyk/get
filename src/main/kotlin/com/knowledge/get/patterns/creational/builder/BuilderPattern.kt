package com.knowledge.get.patterns.creational.builder

fun main() {
    val window = Window.Builder()
        .setName("windowssss")
        .setModal(true)
        .build()
    println(window)
}

class Window private constructor(val builder: Builder) {
    private var name: String? = null
    private var height: Int? = null
    private var modal: Boolean? = null

    init {
        this.name = builder.getName()
        this.height = builder.getHeight()
        this.modal = builder.getModal()
    }

    class Builder {
        private var name: String? = null
        private var height: Int? = null
        private var modal: Boolean? = null

        fun setName(name: String) = this.apply { this.name = name }
        fun setHeight(height: Int) = this.apply { this.height = height }
        fun setModal(modal: Boolean) = this.apply { this.modal = modal }

        fun getName() = this.name
        fun getHeight() = this.height
        fun getModal() = this.modal

        fun build() = Window(this)
    }

    override fun toString(): String {
        return "Window(name=$name, height=$height, modal=$modal)"
    }
}