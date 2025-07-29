package com.knowledge.get.sandbox

import com.knowledge.get.sandbox.model.Book
import com.knowledge.get.sandbox.model.User
import com.knowledge.get.sandbox.repository.InMemoryRepository
import com.knowledge.get.sandbox.service.BookService
import com.knowledge.get.sandbox.service.UserService

fun main() {
    val bookRepo = InMemoryRepository<Book, String> { it.id }
    val bookService = BookService(bookRepo)

    val userRepo = InMemoryRepository<User, String> { it.id }
    val userService = UserService(userRepo)

    bookService.create(Book("1", "Kotlin in Action", "Dmytro Bilyk"))
    bookService.create(Book("2", "Kotlin in Action2", "Dmytro Bilyk2"))

    userService.create(User("1", "Dmytro", "dmytro@gmail.com"))
    userService.create(User("2", "Dmytro2", "dmytro2@gmail.com"))

    println("Books:")
    bookService.getAll().forEach { println(it) }

    val names = listOf("Alice", "Bob", "Charlie")

    fun sayHi(name: String) = println("Hi $name")

    names.forEach(::sayHi)

    fun <T> List<T>.forEachLike(action: (T) -> Unit) {
        for (element in this) {
            action(element)
        }
    }

    fun <T, R> List<T>.likeMap(transform: (T) -> R): List<R> {
        val result = mutableListOf<R>()
        for (element in this) {
            result.add(transform(element))
        }
        return result
    }

    val list = mutableListOf<Book>(
        Book("1", "Kotlin in Action", "Dmytro Bilyk"),
        Book("2", "Kotlin in Action2", "Dmytro Bilyk2"))

    fun getString(book: Book): String {
        return book.title
    }

    list.likeMap(::getString)
        .forEach { println(it) }

    list.likeMap { it.author }
        .forEach { println(it) }


}

