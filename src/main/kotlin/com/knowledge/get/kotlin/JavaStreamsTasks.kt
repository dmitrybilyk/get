package com.knowledge.get.kotlin

import java.util.*
import java.util.function.Supplier
import java.util.stream.*

fun main() {
//    val numbers = listOf(3, 7, 2, 8, 1, 4, 10, 6)
//
//    numbers.stream()
//        .filter { it % 2 == 0 }
//        .max(Comparator.naturalOrder())
//        .orElse(null)

//    val words = listOf("apple", "banana", "avocado", "grape", "apricot", "orange")
//
//    words.stream()
//        .filter { it.startsWith('a', true) }
//        .map { it.uppercase() }
//        .toList()

//    val numbers = listOf(5, 12, 3, 18, 7, 20, 2, 9)
//
//    numbers.stream()
//        .filter { it > 10 }
//        .map { it * it }
//        .mapToInt { it }
//        .average()
//        .orElse(0.0)

//    val words = listOf("Radar", "level", "World", "Deified", "Stream", "Java", "Civic")
//
//    words.stream()
//        .filter { word -> word.equals(word.reversed(), true) }
//        .map { it.lowercase() }
//        .collect(Collectors.toSet())

//    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//
//    val collect = numbers.stream()
//        .collect(Collectors.groupingBy { if (it % 2 == 0) "even" else "odd" })
//    val even = collect["even"]
//
//    val partitioned = numbers.stream()
//        .collect(Collectors.partitioningBy{ it % 2 == 0 })
//
//    println(even)
//    println(partitioned)

//    val words = listOf("apple", "banana", "apricot", "cherry", "avocado", "blueberry", "blackberry")
//    val grouped = words.stream()
//        .collect(Collectors.groupingBy( {it.lowercase().first() }, Collectors.counting() ))

//    val sentences = listOf(
//        "Hello world",
//        "Hi there",
//        "How are you",
//        "World peace"
//    )
//
//    val map = sentences.stream()
//        .map { s: String -> s.split(" ") }
//        .flatMap { it.stream() }
//        .collect(Collectors.groupingBy({ it.lowercase().first() }, Collectors.toSet()))
//        .toSortedMap(Comparator.comparing { it.lowercase() })
//        .forEach { println(it) }

    val books = listOf(
        Book("Book A", "Author X", 2001, 4.5, listOf("Fantasy", "Adventure")),
        Book("Book B", "Author Y", 1999, 4.2, listOf("Horror")),
        Book("Book C", "Author X", 2005, 3.9, listOf("Fantasy", "Drama")),
        Book("Book D", "Author Z", 2010, 4.8, listOf("Adventure")),
        Book("Book E", "Author X", 2020, 4.1, listOf("Drama", "Adventure"))
    )

//    val toList = books.stream()
//        .flatMap { book -> book.genres.stream()
//            .map { genre -> AbstractMap.SimpleEntry(genre.lowercase(), book.rating) } }
//        .collect(Collectors.groupingBy (
//            { it.key },
//            Collectors.averagingDouble { it.value }
//        ))
//        .toSortedMap()

//    val collect = books.stream()
//        .sorted(Comparator.comparingDouble<Book?> { it.rating }.reversed())
//        .collect(Collectors.groupingBy({ it.author }, Collectors.mapping({ it.title }, Collectors.toList())))

//    val collect = books.stream()
//        .collect(
//            Collectors.groupingBy(
//                { it.author },
//                { TreeMap() },
//                Collectors.mapping({
//                    it.title
//                },
//                    Collectors.toCollection {
//                        TreeSet()
//                    }
//                )
//            ))

//    val collect = books.stream()
//        .collect(
//            Collectors.groupingBy(
//                { it.author },
//                Collectors.flatMapping(
//                    { it.genres.stream() },
//                    Collectors.toSet()
//                )
//            )
//        )

//    val collect = books.stream()
//        .collect(Collectors.groupingBy(
//            { it.year > 2010 },
//            Collectors.collectingAndThen(
//                Collectors.flatMapping(
//                    { it.genres.stream() },
//                    Collectors.toCollection { TreeSet(String.CASE_INSENSITIVE_ORDER) }
//                ),
//                { it.joinToString(", ") }
//            )
//        ))


//    println(collect)
//
//    books.stream()
//        .filter { it.year > 2000 && it.rating > 4 }
//        .collect(Collectors.groupingBy({ it.author }, Collectors.averagingDouble { it.rating } ))
//        .toSortedMap(Comparator.comparing { it })

//    books.stream()
//        .sorted(Comparator.comparing<Book?, Double?> { it.rating }.reversed())
//        .limit(3)
//        .collect(Collectors.toList())



//    println(grouped)

}

data class Book(
    val title: String,
    val author: String,
    val year: Int,
    val rating: Double,
    val genres: List<String>
)


