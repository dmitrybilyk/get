package com.knowledge.get.patterns.structural

fun main() {

    val mainDirectory = Directory("main")
    val subDirectory = Directory("subDirectory")
    val fileItem = FileItem("file1")
    val fileItem2 = FileItem("file2")
    val fileItem3 = FileItem("file3")
    subDirectory.addStorage(fileItem3)
    mainDirectory.addStorage(fileItem)
    mainDirectory.addStorage(fileItem2)
    mainDirectory.addStorage(subDirectory)
    mainDirectory.printName()

}

interface Storage {
    val name: String
    fun printName()
}

open class FileItem(override val name: String): Storage {
    override fun printName() {
        return println(name)
    }
}

data class Directory(override val name: String) : Storage {
    private val fileItems: MutableList<Storage> = mutableListOf()

    fun addStorage(storage: Storage) {
        fileItems.add(storage)
    }

    override fun printName() {
        println(name)
        fileItems.forEach { it.printName() }
    }

}