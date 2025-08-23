//package com.knowledge.get.kotlin
//
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//import java.lang.Thread.sleep
//
//data class User(val name: String, val lastName: String)
//
//
//sealed class Human {
//    data class Student(val name: String, val lastName: String) : Human()
//    data class Teacher(val name: String, val lastName: String, val salary: Double) : Human()
//    object Principal: Human() {
//        val car = "Mazda"
//    }
//}
//
//fun getHumanInfo(human: Human) {
//    when (human) {
//        is Human.Student -> { println("Student ${human.name}"); }
//        is Human.Teacher -> { println("Teacher ${human.name} with salary ${human.salary}"); }
//        is Human.Principal -> {
//            println("don't know what to say - ${human.car}")
//        }
//    }
//}
//
//fun Item.normalize(): Item = copy(
//    status = Status.FIRST,
//    price = price.coerceAtLeast(0.0)
//)
//
//fun main() {
////
////    val item = Item("Dmytro item", 200.0, Status.FIRST)
////        .also {
////            println("Original - $it")
////            it.status = Status.SECOND
////        }
////
////    println(item)
////
////    val newItem = with(item) {
////        name = "New name"
////        item
////    }
////
////    newItem.apply {
////        name = "New name2"
////        status = Status.THIRD
////        price = -20.0
////    }
////
////    println(newItem.normalize())
////
////    val item2: Item? = null
////    item2.let {
////        println("Original - ${it?.status}")
////        println("Original2 - $it")
////    }
//
//
//
////    val student = Human.Student("Alice", "Smith")
////    val teacher = Human.Teacher("Teacher", "Smith", 1.0)
////    val principal = Human.Principal
////
////    val humanInfo = getHumanInfo(student)
//
//
////    val userMono = Mono.just(User("John", "Doe"))
////        .subscribe { println(it) }
////
////    val usersFlux = Flux.just(
////        User("Dmytro", "Bilyk"),
////        User("John", "Doe")
////    ).flatMap {
////        Mono.just(it).map { user -> "${user.name.uppercase()} ${user.lastName}" }
////            .map { str ->
////                val split = str.split(" ")
////                split
////            }
////    }
////        .blockFirst()
//////        .subscribe { println(it) }
//
//
//
//
//
////    val namesFlux = Flux.just( "Dmytro", "Dmytro and Andrii" )
////    val lastNamesFlux = Flux.just( "Bilyk", "Bilyk and Gorbatov" )
////
//////    Flux.zip(namesFlux, lastNamesFlux) { name, lastName -> "$name - $lastName" }
//////        .subscribe{ println(it) }
////    Flux.zip(namesFlux, lastNamesFlux) { name, lastName -> User(name, lastName) }
////        .subscribe{ println(it) }
//
//
////    Flux.just(User("2", "4"), User("3", "4"))
////        .subscribe { println(it) }
//
//
//
////    val resultFlux: Flux<Tuple2<String, String>> = namesFlux.zipWith(lastNamesFlux)
//
////        .subscribe{ println(it) }
////        namesFlux
////        .all { it.contains("Dmytro") }
////        .subscribe { println("I'm ready! $it") }
//
//
////
////    val item = Item("ddddd", 4.55, Status.THIRD)
////
////    val (name, age) = item
////
////    println(name)
////
////    println(Status.FIRST.name)
////
////    var items = listOf(
////        Item("fff", 3.44, Status.FIRST),
////        Item("fff", 3.44, Status.FIRST)
////    )
////
////    val map = mutableMapOf("Dmytro" to 33, "Ruslan" to 44)
////    map["Dmytro"] = 44
//
////    useItems(items)
//
////    val namesMap = mutableMapOf("Dmytro2" to 44, "Ruslan" to 47)
////    namesMap.getOrPut("Dmytro") {
////        55
////    }
////
////    val value = namesMap.let {
////        println(it["Dmytro2"])
////        it["Dmytro55"] = 155
////        "dfdf"
////    }
////    println(namesMap["Dmytro55"])
////    println(value)
////
////    val itemParent = Item("name", 33.4, Status.SECOND)
////    itemParent.apply {
////        name = "new name"
////    }
////
////    println(itemParent)
////
////    itemParent.also {
////        println(it)
////        it.name = "new name2"
////    }
////
////    println(itemParent)
////
////    println(itemParent.run {
////        name = "new name3"
////        true
////    })
////
////    println(itemParent)
//
////    val upperCaseString = {  str: String -> str.uppercase() }
////    val list = listOf("dddd")
////    println(list.map { upperCaseString(it) })
////
////    println( { text: String -> text.uppercase() }("hello"))
////
////    val actions = listOf("title", "year", "author")
////    val prefix = "https://example.com/book-info"
////    val id = 5
//////    val urls = // Write your code here
//////        println(urls)
////
////    val la = { action: String -> "$prefix/$id/$action" }
////
////    val urls = actions.map(la)
////    println(urls)
////
////    repeatN(3) { println("something") }
//
////    val people = listOf(
////        Person("Alice", 30),
////        Person("Bob", 17),
////        Person("Charlie", 25),
////        Person("Diana", 15)
////    )
////
////    val ageFilter = { person: Person -> person.age!! > 20 }
////    val toUppercase = { person: Person -> person.name!!.uppercase() }
////    val toSort = { a: String, b: String  -> a.length - b.length }
////    val greetPerson = { person: Person -> "Hello, ${person.name}" }
////
////    val findTeenagers = { person: Person -> person.age!! < 18}
////
////    val addGroup = { person: Person ->
////        when {
////            person.age!! < 18 -> "Teenagers"
////            person.age!! < 60 -> "Adult"
////            else -> "Senior"
////        }
////    }
////
////    val startWithA = { person: Person -> person.name?.first() == 'A'}
////
////    people
////        .filter(startWithA)
////        .map(greetPerson)
////        .forEach { println(it) }
////
////    println(
////        transformPeople(
////            people,
////            ageFilter,
////            toUppercase,
////            toSort,
////            startWithA
////        )
////    )
////
////    println(50.isPositive())
////
////    getString(true).let {
////        println()
////        println("not null - $it")
////        it == null
////    }
////
////    val person = Person().apply {
////        name = "New Name"
////        age = 55
////    }
////
////    val name: String? = null
////
////    val upperName = name?.let {
////        println("Uppercasing: $it")
////        it.uppercase() + "ddddddddd"
////    }
////
////    println(upperName)
////
////    getString(false).apply {
////        println(this)
////    }
//    val product = Product()
//    val priceInEuros = product.getPriceInEuros()
//
//    if (priceInEuros != null) {
//        println("Price in Euros: €$priceInEuros")
//        // Price in Euros: €85.0
//    } else {
//        println("Price information is not available.")
//    }
//
//}
//
//class MenuItem(val name: String)
//
//class Menu(val name: String) {
//    val items = mutableListOf<MenuItem>()
//
//    fun item(name: String) {
//        items.add(MenuItem(name))
//    }
//}
//
//data class ProductInfo(val priceInDollars: Double?)
//
//class Product {
//    fun getProductInfo(): ProductInfo? {
//        return ProductInfo(100.0)
//    }
//}
//
//// Rewrite this function
//fun Product.getPriceInEuros(): Double? {
//    return getProductInfo()?.priceInDollars?.let {
//        convertToEuros(it)
//    }
//}
//
//fun convertToEuros(dollars: Double): Double {
//    return dollars * 0.85
//}
//
//
//fun getString(toReturnNull: Boolean): String? {
//    return if (toReturnNull) null else "Result"
//}
//
//fun Int.isPositive(): Boolean = this > 0
//
//fun transformPeople(people: List<Person>,
//                    filter: (Person) -> Boolean,
//                    transform: (Person) -> String,
//                    sort: (String, String) -> Int,
//                    startsWithA: (Person) -> Boolean
//): List<String> {
//    return people
//        .filter(startsWithA)
//        .map(transform)
//        .sortedWith(Comparator(sort))
//}
//
//fun repeatN(n: Int, action: () -> Unit ) {
//    for (i in 1..n) {
//        action()
//    }
//}
//open class ItemParent {
//    var parentProperty: String = ""
//}
//
//
//data class Person(var name: String? = null, var age: Int? = 0)
//
//
//data class Item(var name: String, var price: Double, var status: Status) : ItemParent()
//
//enum class MyEnum {
//    STARTED {
//        override fun signal() = PAUSED
//    },
//    STOPPED {
//        override fun signal() = PAUSED
//    },
//    PAUSED {
//        override fun signal() = PAUSED
//    };
//
//    abstract fun signal(): MyEnum
//}
//
//enum class Status {
//    FIRST,
//    SECOND,
//    THIRD
//}
//
