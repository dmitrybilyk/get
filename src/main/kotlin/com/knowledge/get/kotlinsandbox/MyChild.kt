package com.knowledge.get.kotlinsandbox

class MyChild: MyParent() {
    fun childFunction() {
        println("child function $name")
    }

    fun functionWithReturn(): String {
        println("function with return $name")
        return name
    }
}