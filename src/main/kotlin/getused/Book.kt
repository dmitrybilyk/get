package getused

data class Book( val title: String, val author: String?, val pages: Int )
data class Chapter(val title: String, val pages: Int)
data class DetailedBook(val title: String, val author: String?, val chapters: List<Chapter>)

fun printBookSummary(book: Book) {
    println("The book ${book.title} by ${book.author ?: "unknown author"} has ${book.pages} pages.")
}

fun main() {
    val bookWithUnknownAuthor = Book("Title", null, 12)
    val bookWithUnknownAuthor2 = Book("Title2", null, 16)
    val book11 = Book("Title", "Author", 1)
    val book1 = Book("Title", "Author", 1)
    val boo2 = Book("Title2", "Author2", 12)

    val listOfBooks = listOf(bookWithUnknownAuthor, bookWithUnknownAuthor2, book11, book1, boo2)
    val longBooks = listOfBooks.filter { it.pages > 10 }
        .sortedBy{ it.pages }

    longBooks.forEach{ println(it.summary()) }

    val groupedBooks = listOfBooks.groupBy { it.author ?: " Unknown" }
    groupedBooks.forEach{ (author, books) ->
        println("Author: $author")
        books.forEach{
            println(" - ${it.title} (${it.pages} pages)")}
    }

    println("\nBook counts per author:")
    groupedBooks.forEach {(author, books) ->
        println("Author: ${author}, counts per author: ${books.size}")
    }

    listOfBooks.groupBy { it.author ?: " Unknown" }
        .forEach { (author, books) ->
            val averagePages = books.map { it.pages }.average()
            println("Author: $author, average pages: $averagePages")
        }

    val detailedBooks = mutableListOf<DetailedBook>(
        DetailedBook("Title", "author",
            listOf(
                Chapter("chaptTitle", 3),
                Chapter("chaptTitle2", 32),
            )
        ),
        DetailedBook("Title2", "author2",
            listOf(
                Chapter("chaptTitle222", 344),
                Chapter("chaptTitle2222", 3244),
            )
        )
    )

    detailedBooks.flatMap { it.chapters }
        .sortedBy{ it.pages }
        .forEach { println("Chapter: ${it.title} (${it.pages})") }

    detailedBooks.groupBy { it.author ?: " Unknown" }
        .forEach { (author, books) ->
            val allChapters = books.flatMap { it.chapters }
            val averagePages = allChapters.map { it.pages }.average()
            println("Author: $author, average chapter pages: $averagePages")
        }

    val context = AreaContext(CircleAreaStrategy(3.0))
    println(context.calculateArea())

    context.setStrategy(SquareAreaStrategy(4.0))
    println(context.calculateArea())
}

fun Book.summary(): String {
    return "The book ${this.title} by ${this.author ?: "unknown author"} has ${this.pages} pages."
}

fun describeShape(shape: Shape): String {
    return when (shape) {
        is Rectangle -> "A rectangle with width ${shape.width} and height ${shape.height}"
        is Circle -> "A circle with radius ${shape.radius}"
        is Square -> "A square with side ${shape.side}"
    }
}

sealed class Shape

data class Circle(val radius: Double) : Shape()
data class Rectangle(val width: Double, val height: Double) : Shape()
data class Square(val side: Double) : Shape()


interface AreaStrategy {
    fun calculateArea(): Double
}

class CircleAreaStrategy(private val radius: Double) : AreaStrategy {
    override fun calculateArea() = Math.PI * radius * radius
}

class SquareAreaStrategy(private val side: Double) : AreaStrategy {
    override fun calculateArea() = side * side
}

class AreaContext(private var strategy: AreaStrategy) {
    fun calculateArea(): Double = strategy.calculateArea()

    fun setStrategy(newStrategy: AreaStrategy) {
        strategy = newStrategy
    }
}