//package com.knowledge.get.kotlin.classes.generics
//
//fun main() {
////    val box = Box(SomeClass("Some value"))
////    println(box.value)
////    val cache = Cache<Int, Int>()
////    listOf(3).map {  }
////    cache.put(5, 4)
////    println(cache[5])
////    val appleBox = Box(Apple())
////    val bananaBox = Box(Banana())
////    println("Apple box has: ${appleBox.getItem()}")
////    println("Banana box has: ${bananaBox.getItem()}")
////
////    val fruits = listOf(Apple(), Banana(), Orange())
////    val fruitPrinter = FruitPrinter()
////    fruitPrinter.printFruitNames(fruits)
////
////    val fruitList = listOf(Fruit("Apple"), Fruit("Banana"), Banana())
////    val appleList = listOf(Apple(), Apple())
////    val bananaList = listOf(Banana(), Banana())
////
////    val appleBasket: FruitBasket<Apple> = FruitBasket(appleList)
////    val fruitBasket: FruitBasket<Fruit> = appleBasket
////
//////    val item: Apple = getItem(0, fruitList)
////    val fruitSink: FruitSink<Fruit> = FruitSink()
////    val appleSink: FruitSink<Apple> = fruitSink
////
////    printAll(fruitList)
//
////    print(Fruit("fruit"))
////    print(Apple())
////    print(Banana())
//
////    val fruitList = listOf(Fruit("apple"), Fruit("banana"))
////    val appleList = mutableListOf(Apple(), Apple())
////    val bananaList = mutableListOf(Banana(), Banana())
////    val fruits: List<Fruit> = appleList
//
////    print(getFruit(fruitList))
//
//    var human = Human()
//    val student = Student()
//    human = student
//
//    var students: List<Student> = mutableListOf(Student())
////    var humans: List<Human> = listOf(Human())
//
//    val humans: List<Human> = students
//
//    val handler: Handler<Human> = HumanHandler()
//    processStudent(handler, Human())
//
//}
//
//open class Human
//class Student: Human()
//
//fun processStudent(handler: Handler<Student>, student: Student) {
//    handler.handle(student)
//}
//
//interface Handler<in T> {
//    fun handle(value: T)
//}
//
//class HumanHandler : Handler<Human> {
//    override fun handle(value: Human) {
//        println("Handling human: ${value::class.simpleName}")
//    }
//}
//
//fun <T: Fruit> print(item: T) {
//    println(item.name)
//}
//
//fun <T: Fruit> getFruit(list: MutableList<T>): T {
//    return list[0]
//}
//
//fun <T: Fruit> printAll(fruits: List<T>) {
//    fruits
//        .sortedWith(Comparator.comparing { it.name })
//        .forEach { println(it.name) }
//}
//
//class FruitSink<in T : Fruit> {
//    fun consume(fruit: T) {
//        println("Consumed: ${fruit.name}")
//    }
//}
//
//
//class FruitBasket<out T : Fruit>(private val items: List<T>) {
//    fun get(index: Int): T = items[index]
//
//    fun getItem(index: Int): T = items[index]
//}
//
//fun <T: Fruit> getItem(index: Int, items: List<T>): T = items[index]
//
//class FruitPrinter {
//    fun <T: Fruit> printFruitNames(fruits: List<T>) {
//        fruits.forEach { fruit -> print("Fruit: ${fruit.name}") }
//    }
//}
//
//class Box<T>(private val item: T) {
//    fun getItem(): T = item
//}
//
//open class Fruit(val name: String): Comparable<Fruit> {
//    override fun compareTo(other: Fruit): Int {
//        return other.name.compareTo(name)
//    }
//
//    override fun toString() = name
//}
//
//class Apple : Fruit("Apple")
//class Banana : Fruit("Banana")
//class Orange : Fruit("Orange")
//
//
//fun <T, R> mapList(mapList: List<T>, transform: (T) -> R): List<R> {
//    return mapList.map { transform(it) }
//}
//
//class Cache<K, V : Number> {
//    private val cache: MutableMap<K, V> = mutableMapOf()
//
//    fun put(key: K, value: V) {
//        cache[key] = value
//    }
//
//    operator fun get(key: K): V? {
//        return cache[key]
//    }
//
//    fun getOrDefault(key: K, default: V): V {
//        return cache.getOrDefault(key, default)
//    }
//
//    fun contains(key: K): Boolean {
//        return cache.containsKey(key)
//    }
//
//    fun size(): Int {
//        return cache.size
//    }
//
//    fun remove(key: K): V? {
//        return cache.remove(key)
//    }
//
//    fun clear() {
//        cache.clear()
//    }
//
//    override fun toString(): String {
//        return cache.toString()
//    }
//}
//
//data class SomeClass(val someValue: String)
//
//interface GenericInterface<T> {
//    fun returnValue(): T
//}
//
//class GenericClass: GenericInterface<String> {
//    override fun returnValue(): String {
//        return "dfdfd"
//    }
//}
//
//class GenericIntClass: GenericInterface<Int> {
//    override fun returnValue(): Int {
//        return 77
//    }
//}
//
//class Producer<out T>(private val value: T) {
//    fun get(): T = value
//}
//
//val strProducer: Producer<String> = Producer("Hello")
//val anyProducer: Producer<Any> = strProducer
//
//class Consumer<in T>() {
//    fun accept(value: T) {
//        println(value)
//    }
//}
//
//val anyConsumer: Consumer<Any> = Consumer()
//val strConsumer: Consumer<Any> = anyConsumer