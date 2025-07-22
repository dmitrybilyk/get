package com.knowledge.get.kotlinsandbox

class Student(var name:String, var age: Int, var graduated: Boolean) {
    fun study(number: Long) {
        println("Student student called $number")
    }
}

fun main() {
    val student = Student("Dmytro", 33, true)

    println(student.name)
    println(student.age)
    println(student.graduated)
    student.study(11)
}