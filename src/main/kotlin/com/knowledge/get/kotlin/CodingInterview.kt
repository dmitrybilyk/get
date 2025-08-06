package com.knowledge.get.kotlin

fun main() {
    printSum(3, 6)

    println(checkEvenOdd(4))

    print1n(5)

    println(reverseString("Dmytro"))

    println(checkPallindrom("roporttt"))
}

fun checkPallindrom(s: String): Boolean {
    var left = 0
    var right = s.length - 1

    while (left < right) {
        if (s[left] != s[right]) {
            return false
        }
        left++
        right--
    }
    return true
}

fun reverseString(s: String): String {
    val result = StringBuilder()
    for (i in s.length -1 downTo 0) {
        result.append(s[i])
    }
    return result.toString()
}

fun print1n(n: Int) {
    listOf(1..n)
        .flatMap { it.toList() }
        .forEach { println(it) }
}

fun printSum(a: Int, b: Int) = println(a + b)

fun checkEvenOdd(value: Int): String {
    return if (value % 2 == 0) "Even" else "Odd"
}
