package com.knowledge.get.patterns.creational.factory.abstractfactory

fun main() {
    val factory = LinuxUICreationFactoryImpl()
    val button = factory.createButton()
    button.click()
    val window = factory.createWindow()
    window.collapse()
}

interface UICreationFactory {
    fun createButton(): Button
    fun createWindow(): Window
}

class WindowsUICreationFactoryImpl: UICreationFactory {
    override fun createButton(): Button {
        return WindowsButton()
    }

    override fun createWindow(): Window {
        return WindowsWindow()
    }
}

class LinuxUICreationFactoryImpl: UICreationFactory {
    override fun createButton(): Button {
        return LinuxButton()
    }

    override fun createWindow(): Window {
        return LinuxWindow()
    }
}

interface Button {
    fun click()
}
interface Window {
    fun collapse()
}

class WindowsButton: Button {
    override fun click() {
        println("Clicking the Windows button")
    }
}

class WindowsWindow: Window {
    override fun collapse() {
        println("Collapsing the Window in Windows")
    }
}

class LinuxButton: Button {
    override fun click() {
        println("Clicking the Linux button")
    }
}

class LinuxWindow: Window {
    override fun collapse() {
        println("Collapsing the Window in Linux")
    }
}