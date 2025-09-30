package com.knowledge.get.patterns.structural

fun main() {
    val carFacade = CarFacade(Car())
    carFacade.carCheckup()
    carFacade.turnOnRelaxMode()
}

class Car {
    fun checkWheels() {
        println("checking wheels")
    }

    fun checkTrunk() {
        println("checking trunk")
    }

    fun checkMirrors() {
        println("checking mirrors")
    }

    fun turnOnTheMusic() {
        println("turning on the music")
    }

    fun switchingOnTheConditioner() {
        println("switching conditioner")
    }
}

class CarFacade(private val car: Car) {
    fun carCheckup() {
        car.checkMirrors();
        car.checkTrunk();
        car.checkWheels();
    }

    fun turnOnRelaxMode() {
        car.switchingOnTheConditioner()
        car.turnOnTheMusic()
    }
}