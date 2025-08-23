package com.knowledge.get.kotlin

import java.util.stream.*

fun main() {
//    val titles = listOf("Kotlin", "Java", "Python", "C#", "JavaScript", "Go")
//
//    val collect = titles
//        .filter { it.length > 5 }
//        .map { it.uppercase() }
//        .sorted()
//
//    println(collect)

//    val words = listOf("Apple", "apricot", "banana", "blueberry", "cherry", "cranberry", "avocado")
//
//    val collect = words.stream()
//        .collect(
//            Collectors.groupingBy(
//                { it.first() },
//                Collectors.counting()
//            )
//        )
//
//    val collectMap = words.groupBy { it.first() }
//        .mapValues { it.value.size }
//
//    println(collectMap)

//    val words = listOf("Apple", "Ape", "Banana", "Berry", "Cat", "Car", "Dog", "Deer")
//
//    val resultMap = words
//        .groupBy( { it.length }, { it.first().lowercaseChar() } )
//        .mapValues { it.value.toSet() }
//        .toSortedMap()
//
//    println(resultMap)

//    val words = listOf("apple", "orange", "umbrella", "elephant", "ice", "owl", "ant", "iguana", "echo")
//
//    val vowels = listOf("A", "E", "I", "O", "U")
//
//    val take = words.filter { vowels.contains(it.first().uppercase()) }
//        .sortedByDescending { it.length }
//        .take(3)
//
//    println(take)

//    val words = listOf("apple", "apricot", "banana", "blueberry", "cherry", "cranberry", "avocado")
//
//    val groupBy = words.groupBy({ it.first() }, { it.length })
//        .mapValues { it.value.average() }
//
//    println(groupBy)

    val words = listOf("apple", "bat", "car", "apricot", "dog", "cat", "ant")

    val mapValues = words.groupBy(
        { it.length },
        { it.uppercase() })
        .mapValues { it.value }
        .toSortedMap()

    println(mapValues)


}