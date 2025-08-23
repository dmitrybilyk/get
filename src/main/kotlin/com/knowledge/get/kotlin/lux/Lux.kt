package com.knowledge.get.kotlin.lux

fun main() {
//    val doc = Document("doc123", mapOf("status" to "pending", "version" to 1), true)
//    println(doc.processDocument())
//
//    val emptyDoc = Document()
//    println(emptyDoc.processDocument())

//    Create an extension function for the Int type called isEven().
//    It should return true if the number is even, and false if not.
//    println("should return true".capitalizeWords())

//    val counter = mutableMapOf("a" to 2, "b" to 5)
//    counter.incrementValue("a")
//    counter.incrementValue("c")
//    println(counter)

//    val rectangle = Rectangle(41, 43)
//    println(rectangle.isSquare)
}

enum class Color(
    var r: Int,
    val g: Int,
    val b: Int
) {
    RED(4, 3, 2),
    BLUE(6, 7, 3);

    var rgb = (r * g * b)
}

fun measureColor() = Color.RED

fun getMnemonic(color: Color) {
    when (val color = measureColor()) {
        Color.RED -> "red ${color.g}"
        Color.BLUE -> "blue"
        else -> "fffck color"
    }
}


class Rectangle {
    val isSquare: Boolean
        get() {
            return height == width
        }

    private var height: Int = 0
        set(value) {
            if (value > 0) {      // only allow positive values
                field = value     // `field` is the backing property
            } else {
                println("Height must be positive")
            }
        }

    private var width: Int = 0
        set(value) {
            if (value > 0) {
                field = value
            } else {
                println("Width must be positive")
            }
        }
}


//fun MutableMap<String, Int>.incrementValue(key: String) {
//   this.computeIfPresent(key, { key, value ->  })
//}

fun Int.isEvent(): Boolean {
    return this % 2 == 0
}

fun String.lastChar(): Char {
    return this.last()
}

fun String.isPalindrome(): Boolean {
    return this.uppercase() == this.reversed()
}

fun List<Int>.secondLargest(): Int {
    return this.sorted()[1]
}

fun String.capitalizeWords(): String {
    return this.split(" ")
        .joinToString { it.first().uppercase() }
}

fun List<Int>.average(): Int {
    return this.sum() / this.size
}


data class Person(val firstName: String, val lastName: String)

fun Person.formatInitials(): String {
    return if(firstName.isBlank() && lastName.isBlank()) {
        "N/A"
    } else
        "${firstName.first()}.${lastName.first()}."
}

data class Document(
    val documentId: String? = null,
    val metadata: Map<String, Any?> = emptyMap(),
    val isSubmitted: Boolean = false
) {
    fun addMetadata(key: String, value: Any?) {
        metadata.plus(Pair(key, value))
    }
}

fun Document.updateMetadata(key: String, value: Any?): Document {
    return if (!metadata.containsKey(key)) {
        this.copy(metadata = metadata.plus(key to value))
    } else {
        this
    }
}

fun Document.validateId(): String {
    return when {
        documentId?.isBlank() == true -> "No valid document ID"
        else -> "Document ID: [$documentId]"
    }
}

fun Document.processDocument(): String {
    return buildString {
        append("Document ID: ${documentId ?: "N/A"}")
        if (isSubmitted) append(" (Submitted)")
        metadata.forEach { (key, value) ->
            append("\n  $key: ${value?.toString() ?: "null"}")
        }
    }
}