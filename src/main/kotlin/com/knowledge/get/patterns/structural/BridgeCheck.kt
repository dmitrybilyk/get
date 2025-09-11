package com.knowledge.get.patterns.structural

fun main() {
    val amazonVoiceEngine = AmazonVoiceEngine()
    val customVoiceEngine = CustomVoiceEngine()
    val robot: Robot = MiddleSizeRobot(GoogleVoiceEngine())
    robot.talk()
}

abstract class Robot(protected val voiceEngine: VoiceEngine) {
    abstract fun talk()
    abstract fun returnResult(): Int
}

class SmallRobot(voiceEngine: VoiceEngine) : Robot(voiceEngine) {
    override fun talk() {
        println("I'm small robot with voice engine ${voiceEngine.gender()} and quality ${voiceEngine.quality()}")
    }

    override fun returnResult(): Int = 40
}

class MiddleSizeRobot(voiceEngine: VoiceEngine) : Robot(voiceEngine) {
    override fun talk() {
        println("I'm middle size robot with voice engine ${voiceEngine.gender()} and quality ${voiceEngine.quality()}")
    }

    override fun returnResult(): Int = 100
}

interface VoiceEngine {
    fun quality(): String
    fun gender(): String
}

class AmazonVoiceEngine : VoiceEngine {
    override fun quality(): String = "average"
    override fun gender(): String = "male"
}

class CustomVoiceEngine : VoiceEngine {
    override fun quality(): String = "low quality"
    override fun gender(): String = "male"
}

class GoogleVoiceEngine : VoiceEngine {
    override fun quality(): String = "super quality"
    override fun gender(): String = "female"
}