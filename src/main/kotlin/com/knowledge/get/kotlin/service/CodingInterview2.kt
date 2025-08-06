package com.knowledge.get.kotlin.service

fun main() {
//    println(findMax(listOf(4, 3, 6, 2, 6)))
//
//    println(countVowels("Dmytro"))
//
//    printFizzBuzz()

//    println(factorial(5))

      println(sumOfDigits(434))

    println(sumOfDigitsInNumber(434))

    println(sumOfDigits(434))
}

fun sumOfDigitsInNumber(number: Int): Int {
    var sum = 0
    var num = number
    while (num > 0) {
        sum += num % 10
        num /= 10
    }
    return sum
}

fun sumOfDigitsLambdas(number: Int): Int {
    return number.toString().map { it.digitToInt() }.sum()
}

fun factorial(n: Long): Long {
    return if (n <= 1) {
        1L
    } else {
        n * factorial(n - 1)
    }
}

fun sumOfDigits(n: Int): Int {
    var num = n
    var sum = 0
    while (num > 0) {
        sum += num % 10
        num /= 10
    }
    return sum
}

fun printFizzBuzz() {
    for (i in 1..100) {
        when {
            i % 15 == 0 -> { println("Fizz-buzz") }
            i % 3 == 0 -> { println("Fizz") }
            i % 5 == 0 -> { println("Buzz") }
            else -> { println("$i") }
        }
    }
}

fun countVowels(s: String): Int {
    val vowels = setOf('a', 'e', 'i', 'o', 'u')
    return s.count { it.lowercaseChar() in vowels }
}

fun findMax(list: List<Int>): Int {
    var max = list.first()
    list.forEach {
        if (it > max) {
            max = it
        }
    }
    return max
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
