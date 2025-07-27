package com.knowledge.get

fun main() {
    val map = mutableMapOf<String, MutableList<String>>();

    val category = "fruits"

    val item = "apple"

//    map[category] = mutableListOf(item)

    map.computeIfAbsent(category) { mutableListOf() }
//    map.computeIfPresent(category) { mutableListOf() }

//    println( map.computeIfAbsent(category) { mutableListOf() }.add(item) )

    println(map[category])

    val words = listOf("apple", "banana", "apple", "orange", "banana", "apple")

    val countsMap = mutableMapOf<String, Int>()

    for (word in words) {
        countsMap.computeIfAbsent(word) { 0 }
        countsMap[word] = countsMap[word]!! + 1
    }

    println(countsMap)


    val people = listOf(
        "Alice" to 30,
        "Bob" to 25,
        "Carol" to 30,
        "Dave" to 25
    )

    val grouped = mutableMapOf<Int, MutableList<String>>()

    for ((name, age) in people) {
        grouped.computeIfAbsent(age) { mutableListOf() }.add(name)
    }



    val data = listOf(
        Triple("Alice", "Math", 90),
        Triple("Bob", "Math", 85),
        Triple("Alice", "Physics", 95),
        Triple("Alice", "Math", 88),
        Triple("Bob", "Physics", 92),
        Triple("Bob", "Math", 78)
    )

    val scores = mutableMapOf<String, MutableMap<String, MutableList<Int>>>()



    for ((student, subject, grade) in data) {
        val subjectMap = scores.computeIfAbsent(student) { mutableMapOf() }
        val scoreList = subjectMap.computeIfAbsent(subject) {mutableListOf()}
        scoreList.add(grade)
    }

    println(scores)


    val orders = listOf(
        Triple("Dmytro", "Laptop", 1),
        Triple("Anna", "Phone", 2),
        Triple("Dmytro", "Laptop", 1),
        Triple("Anna", "Laptop", 1),
        Triple("Anna", "Phone", 3),
        Triple("Dmytro", "Monitor", 2)
    )


    val products = mutableMapOf<String, MutableMap<String, MutableList<Int>>>()

    for ((customer, product, quantity) in orders) {
        val customerMap = products.getOrPut(customer) { mutableMapOf() }
        val productsList = customerMap.getOrPut(product) {mutableListOf()}
        productsList.add(quantity)
    }

    println(products)


    val productsMap = mutableMapOf<String, Int>(
        Pair("Dmytro", 1),
        Pair("Anna", 1),)

    productsMap.putIfAbsent("Dmytro", 4)
    productsMap.putIfAbsent("Ruslan", 4)

    val value = map.getOrElse("Dmytro") { "default" }


    productsMap.getOrPut("Dmytro5") { 0 }

    val cities = listOf(
        "Kyiv" to "Ukraine",
        "Lviv" to "Ukraine",
        "Paris" to "France",
        "Lyon" to "France",
        "Berlin" to "Germany"
    )

    val countryCitiesMap = mutableMapOf<String, MutableList<String>>()

    for ((city, country) in cities) {
        countryCitiesMap.getOrPut(country) { mutableListOf() }.add(city)
    }
    println(countryCitiesMap)

    val registrations = listOf(
        "alice" to "alice@example.com",
        "bob" to "bob@example.com",
        "alice" to "alice@newmail.com"
    )

    val nameEmailMap = mutableMapOf<String, String>()
    for (registration in registrations) {
        nameEmailMap.putIfAbsent(registration.first, registration.second)
    }

    println(nameEmailMap)

    val names = listOf("Alice", "Bob", "Alex", "Brian", "Charlie")

    val namesCharsMap = mutableMapOf<Char, MutableList<String>>()

    for (name in names) {
        namesCharsMap.computeIfAbsent(name[0]) { mutableListOf() }.add(name)
    }

    println(namesCharsMap)

    val stock = mutableMapOf("Laptop" to 10, "Phone" to 5)
    val order = listOf("Laptop", "Phone", "Tablet")

    for (product in order) {
        stock.computeIfPresent(product) { _, quantity -> quantity - 1 }
    }

    val votes = listOf("Anna", "Bob", "Anna", "Anna", "Bob")
    val voteCount = mutableMapOf<String, Int>()

    for (name in votes) {
        voteCount.compute(name) { _, count -> (count ?: 0) + 1 }
    }

    println(voteCount)


    val word = "banana"

    val charCount = mutableMapOf<Char, Int>()

    for (char in word) {
        charCount.compute(char) { _, count -> (count ?: 0) + 1 }
    }
    println(charCount)
}

class TrickyLambdas {
}