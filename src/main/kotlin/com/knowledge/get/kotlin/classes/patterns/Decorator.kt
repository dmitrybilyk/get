package com.knowledge.get.kotlin.classes.patterns

fun main() {
    val housePainter = HousePainterSuperEnhancedDecorator(
        HousePainterEnhancedDecorator(
            HousePainterImpl()))
    housePainter.paint()
}

interface HousePainter {
    fun paint()
}

class HousePainterImpl : HousePainter {
    override fun paint() {
        println("painting house")
    }
}

class HousePainterEnhancedDecorator(private val housePainter: HousePainter): HousePainter {
    override fun paint() {
        housePainter.paint()
        println("painting house in enhanced way")
    }
}

class HousePainterSuperEnhancedDecorator(private val housePainter: HousePainter): HousePainter {
    override fun paint() {
        housePainter.paint()
        println("painting house in enhanced way even better")
    }
}