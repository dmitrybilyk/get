package com.knowledge.get.kotlin

fun main() {
    val item = Item("ddddd", 4.55, Status.THIRD)

    val (name, age) = item

    println(name)

    println(Status.FIRST.name)

    var items = listOf(
        Item("fff", 3.44, Status.FIRST),
        Item("fff", 3.44, Status.FIRST)
    )

    val map = mutableMapOf("Dmytro" to 33, "Ruslan" to 44)
    map["Dmytro"] = 44

//    useItems(items)

}

open class ItemParent {
    var parentProperty: String = ""
}

data class Item(var name: String, var price: Double, var status: Status) : ItemParent()

enum class MyEnum {
    STARTED {
        override fun signal() = PAUSED
    },
    STOPPED {
        override fun signal() = PAUSED
    },
    PAUSED {
        override fun signal() = PAUSED
    };

    abstract fun signal(): MyEnum
}

enum class Status {
    FIRST,
    SECOND,
    THIRD
}

