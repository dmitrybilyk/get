package com.knowledge.get.kotlin.classes.patterns

fun main() {
    val boatBuildingTemplate = BoatBuildingTemplateFactory.createBoatBuildingTemplate()
    boatBuildingTemplate.buildBoat()
}

class Context(val boatBuildingTemplate: BoatBuildingTemplate)

class BoatBuildingTemplateFactory {
    // we assume we load boat type here from some property and determine building template base on it
    companion object {
        fun createBoatBuildingTemplate(): BoatBuildingTemplate {
            val propulsionStrategy = EnginePropulsion()
            return LuxuryBoatBuilding(propulsionStrategy)
        }
    }
}

abstract class BoatBuildingTemplate(val propulsionStrategy: PropulsionStrategy) {
    fun buildBoat() {
        buildMainPart()
        buildAdditionalPart()
        buildExtraPart()
        println(propel())
    }

    private fun buildMainPart() {
        println("Building the main part using ${createMaterial()}")
    }

    private fun propel(): String = propulsionStrategy.propel()

    abstract fun buildAdditionalPart()
    abstract fun buildExtraPart()

    abstract fun createMaterial(): Material

}

class LuxuryBoatBuilding(propulsionStrategy: PropulsionStrategy) : BoatBuildingTemplate(propulsionStrategy) {
    override fun buildAdditionalPart() {
        println("Building the luxury additional part in boat")
    }

    override fun buildExtraPart() {
        println("Building the luxury extra part in boat")
    }

    override fun createMaterial(): Material {
        return LuxuryMaterial("Luxury material")
    }
}


class CheepBoatBuilding(propulsionStrategy: PropulsionStrategy) : BoatBuildingTemplate(propulsionStrategy) {
    override fun buildAdditionalPart() {
        println("Building the cheep additional part in boat")
    }

    override fun buildExtraPart() {
        println("Building the cheep extra part in boat")
    }

    override fun createMaterial(): Material {
        return CheepMaterial("Cheep material")
    }
}

open class Material(val name: String) {
    override fun toString(): String {
        return name
    }
}

class LuxuryMaterial(name: String): Material(name)
class CheepMaterial(name: String): Material(name)

interface PropulsionStrategy {
    fun propel(): String
}

class EnginePropulsion : PropulsionStrategy {
    override fun propel(): String = "Using an engine for propulsion"
}

class SailPropulsion : PropulsionStrategy {
    override fun propel(): String = "Using a sail for propulsion"
}