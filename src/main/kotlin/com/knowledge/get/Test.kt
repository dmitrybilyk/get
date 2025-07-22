package com.knowledge.get


    fun main() {
//        var name = "Dmytro"
        val name2: String? = "dfdf"
        println(name2)
        println("Hello Worlds")

        val time = 10
        println(if (time > 30) "bigger" else "smaller")

        var day = 3

        val result = when (day) {
            3 -> "Sunday"
            4 -> "Monday"
            else -> "Friday"
        }
        println(result)


        val strins = listOf("First", "Second", "Third")
        for (str in strins) {
            println(str)
        }

        val numbers2 = listOf(1, 2, 3, 4, 5)

        for (numbers in 4..8) {
            if (4 in numbers2) {
                println(4)
            }
            println(numbers)
        }

        fun shortFunction(a: Int, b: Int):Int = a + b
        println(shortFunction(1,2))


        fun anotherShortFunction(s: String): String = s + "A" + "B"


        var str = "Hello Kotlin let"
        str.let { println("$it!!") }
        println(str)
        var strLength = str.let { "$it function".length }
        println("strLength is $strLength")


        var someString = "Some String VAlue"
        someString.let{
            println(it + "ddd")
        }

        someString.run{
            println(this + "ddd")
        }


        val user = User().apply {
            println("dfdf")
            name = "Alice"
            age = 40
            println("User created with name: ${this.name} and age: ${this.age}")
        }

        val name: String? = "Dmytro"

        val length = name?.let {
            println("Name is $it")
            it.length
        }

        println(length)

        var res = "Dmytro".run {
            println("Working on $this")
            "$this Kotlin"
        }
        println(res)

    }

class User() {
    var name: String = ""
    var age: Int = 0
}