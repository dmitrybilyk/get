package com.knowledge.get.kotlin

import java.util.stream.Collectors

fun main() {
//    val intList = listOf(4, 1, 2, 2, 5, 4)
//
//    intList.distinct().sortedByDescending { it }
//        .forEach { println( it * 2 ) }

//    val words = listOf("apple", "banana", "kiwi", "avocado", "apricot", "blueberry")
//
//    val groupingBy = words.groupBy { it.first() }
//        .mapValues { (_, value) -> value.size }
//        .toSortedMap()
//
//    println(groupingBy)

//    val orders = listOf(30.0, 70.0, 120.0, 50.0, 200.0)
//
//    println( "Total after discount: " +
//            orders
//        .filter { it > 50 }
//        .sumOf { it * (1 - .10) } )

//    val people = listOf(
//        Person("Alice", 30),
//        Person("Bob", null),
//        Person("Charlie", 25),
//        Person("Diana", null)
//    )

//    val knownAges = people.mapNotNull { it.age }
//    if (knownAges.isNotEmpty()) {
//        println("Average age: ${knownAges.average()}")
//    } else {
//        println("No age data available")
//    }

//    val map = people.mapNotNull { it.age } .average()
//
//    println(map)
//    val people = listOf(
//        Person("name1", "nickname1"),
//        Person("name2", null),
//        Person("name3", "nickname3"),
//    )
//
//    people.forEach { println( it.greet() ) }

//    val numbers = listOf(1, 2, 3, 4, 5)
//    val result = applyToNumbers(numbers) { it * 3 }
//    println(result)

    val students = listOf(
        Student("Nam1", 19, 20.0),
        Student("Nam2", 15, 150.0),
        Student("Nam7", 19, 20.0),
        Student("DDDD", 19, 15.0),
        Student("ggggg", 19, 20.0),
        Student("aaaa", 19, 120.0),
        Student("hhhh", 19, 200.0),
    )

    students.filter { it.grade > 80 }
        .forEach { (name, grade) -> println("$name: $grade") }

}

data class Student(val name: String, val age: Int = 18, val grade: Double )

data class Person(val name: String, val nickName: String?) {
    fun greet(): String {
        return if(!nickName.isNullOrBlank()) {
            "Hello, $nickName"
        } else {
            "Hello, $name"
        }
    }
}

fun applyToNumbers( list: List<Int>, transform: (Int) -> Int ): List<Int> {
    return list.map{ transform(it) }
}
