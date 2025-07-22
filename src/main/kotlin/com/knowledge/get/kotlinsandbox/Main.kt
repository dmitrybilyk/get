package com.knowledge.get.kotlinsandbox

fun main() {
    val myParent = MyChild();
    println(myParent.name)
    myParent.childFunction()
    println(myParent.functionWithReturn())
}