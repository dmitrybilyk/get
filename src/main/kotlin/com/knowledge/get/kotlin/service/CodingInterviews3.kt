package com.knowledge.get.kotlin.service

fun main() {
    val library = Library()
    val book = Book("1", "Kotlin in Action", "Dmitry Jemerov", true)
    val user = User("u1", "Dmytro", mutableListOf())

    library.addBook(book)
    library.registerUser(user)

    library.borrowBook("1", "u1")
    library.returnBook("1", "u1")
}

data class Book(val id: String, val title: String, val author: String, var isAvailable: Boolean)

data class User(val id: String, val name: String, val borrowedBooks: MutableList<Book>) {
    private val booksLimit = 3

    fun borrowBook(bookToBorrow: Book) {
        if (borrowedBooks.find{book: Book -> book.id == bookToBorrow.id } != null) {
            throw IllegalStateException("Can't borrow the book. The book is already borrowed by this user!")
        }
        if (borrowedBooks.size == booksLimit) {
            throw IllegalStateException("Can't borrow the book. User's limit is exceeded")
        }
        bookToBorrow.isAvailable = false
        borrowedBooks.add(bookToBorrow)
    }

    fun returnBook(book: Book): Boolean {
        return borrowedBooks.remove(book)
    }
}

class Library {
    private val books: MutableList<Book> = mutableListOf()
    private val users: MutableList<User> = mutableListOf()
    private val borrowingHistory = mutableListOf<String>()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun registerUser(user: User) {
        users.add(user)
    }

    fun borrowBook(bookId: String, userId: String) {
        val book: Book? = books.find { it.id == bookId }
        val user: User? = users.find { it.id == userId }

        if (book != null) {
            user?.borrowBook(book)
            books.removeIf { it.id == bookId }
            borrowingHistory.add("User with id $userId has borrowed the book $book")
        }
    }

    fun returnBook(bookId: String, userId: String) {
        val book: Book? = books.find { it.id == bookId }
        book?.isAvailable = true
        borrowingHistory.add("User with id $userId has returned the book $book")
    }

    fun listAvailableBooks(): MutableList<Book> {
        return books
    }
}
