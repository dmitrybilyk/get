//package com.knowledge.get.kotlin.classes.patterns
//
//fun main() {
//    val factory = LuxuryVehicleBuildingFactory()
//    factory.
//
//}
//
//interface VehicleBuildingAbstractFactory {
//    fun buildCorpus(): Corpus
//    fun buildEngine(): Engine
//}
//
//class LuxuryVehicleBuildingFactory : VehicleBuildingAbstractFactory {
//    override fun buildCorpus(): Corpus {
//        return LuxuryCorpus()
//    }
//
//    override fun buildEngine(): Engine {
//        return LuxuryEngine()
//    }
//
//}
//
//class CheepVehicleBuildingFactory : VehicleBuildingAbstractFactory {
//    override fun buildCorpus(): Corpus {
//        return CheepCorpus()
//    }
//
//    override fun buildEngine(): Engine {
//        return CheepEngine()
//    }
//
//}
//
//interface Vehicle
//class LuxuryVehicle: Vehicle
//class CheepVehicle: Vehicle
//
//interface Corpus
//class LuxuryCorpus : Corpus
//class CheepCorpus : Corpus
//
//
//interface Engine
//class LuxuryEngine : Engine
//class CheepEngine : Engine