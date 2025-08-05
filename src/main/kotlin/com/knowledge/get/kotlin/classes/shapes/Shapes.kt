package com.knowledge.get.kotlin.classes.shapes

interface Shape {
    fun area(): Double
}

interface Drawable {
    fun draw(): String
}

abstract class Polygon(val name: String, protected open val printer: Printable) : Shape {
    fun info():String = "Shape $name"
    fun print(): String = printer.print()
}

class Rectangle(
    name: String,
    private val length: Double,
    private val width: Double): Polygon(
    name,
    printer = BasicPrinter("Rectangle with size $length X $width")), Drawable {

    override fun area(): Double {
        return length * width
    }

    override fun draw(): String {
        return "Drawn ▭▭▭"
    }
}

class Triangle(
    name: String,
    private val base: Double,
    private val height: Double
) : Polygon(
    name,
    printer = BasicPrinter("Triangle base=$base, height=$height")), Drawable {

    override fun area(): Double {
        return 0.5 * base * height
    }

    override fun draw(): String {
        return "Drawn △"
    }
}

class Circle(name: String, private val radius: Double): Polygon(name,
    printer = BasicPrinter("Triangle radius $radius")) {
    override fun area(): Double {
        return Math.PI * radius * radius
    }
}

fun main() {
    val sortingLambda = { a: Shape, b: Shape -> b.area().compareTo(a.area()) }

    val shapesList = listOf(
        Rectangle("Rect1", 22.0, 44.0),
        Rectangle("Rect1",21.0, 44.0),
        Triangle("Triangle1", 22.0, 44.0),
        Circle("Circle",  22.0),
    )

    shapesList
        .sortedWith(Comparator(sortingLambda))
        .forEach { shape ->
            if (shape is Drawable) {
                println(shape.draw())
            }
            println(shape.print())
            println(shape.area())
    }
}

interface Printable {
    fun print(): String
}

class BasicPrinter(private val content: String) : Printable {
    override fun print(): String = "Printing: $content"
}