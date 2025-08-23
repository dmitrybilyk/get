package com.knowledge.get.patterns.creational.factory.abstractfactory

fun main() {
    val familyFactory = GoodFamilyFactory()
    val father = familyFactory.createFather()
    val mother = familyFactory.createMother()
    val child = familyFactory.createChild()
    father.speak()
    mother.speak()
    child.speak()
}

interface FamilyFactory {
    fun createFather(): FamilyMember
    fun createMother(): FamilyMember
    fun createChild(): FamilyMember
}

class GoodFamilyFactory: FamilyFactory {
    override fun createFather() = Father("Good father")

    override fun createMother() = Mother("Good mother")

    override fun createChild() = Child("Good child")
}

class BadFamilyFactory: FamilyFactory {
    override fun createFather() = Father("Bad father")

    override fun createMother() = Mother("Bad mother")

    override fun createChild() = Child("Bad child")
}

abstract class FamilyMember(open val memberQuality: String) {
    abstract fun speak()
}

data class Father(override val memberQuality: String) : FamilyMember(memberQuality) {
    override fun speak() {
        println("$memberQuality is speaking")
    }
}

data class Mother(override val memberQuality: String) : FamilyMember(memberQuality) {
    override fun speak() {
        println("$memberQuality is speaking")
    }
}
data class Child(override val memberQuality: String) : FamilyMember(memberQuality) {
    override fun speak() {
        println("$memberQuality is speaking")
    }
}