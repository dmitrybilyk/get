//package com.knowledge.get.kotlin
//
//data class Book(
//    val title: String,
//    val author: String,
//    val price: Double,
//    val genre: String?
//)
//
//fun List<Book>.applyDiscount(discountPercentage: Double = 0.1): List<Book> {
//    return this.map {
//        it.copy(price = it.price * (1 - discountPercentage))
//    }
//}
//
//fun List<Book>.filterByGenre(genre: String): List<Book> {
//    return this.filter { it.genre?.equals(genre, ignoreCase = true) == true }
//}
//
//fun main() {
//    val bookList = listOf(
//        Book("title1", "author1", 40.0, "genre1"),
//        Book("title2", "author2", 150.0, "genre2"),
//        Book("title8", "author2", 10.0, "genre2"),
//        Book("title0", "author0", 30.0, "genre0")
//    )
//
//    bookList
//        .filterByGenre("genre2")
//        .applyDiscount( 0.3 )
//        .forEach { println("Title by Author: ${it.price}") }
//
//
//}
