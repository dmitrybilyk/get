package com.knowledge.get.patterns.behavioral.command

fun main() {
    val coachService = CoachService()
    val workCommand = ActiveWorkCommand(coachService)
    val relaxCommand = RelaxCommand(coachService)
    val manager = Manager(Person("Dmytro"))
    manager.setCommand(workCommand)
    manager.manage()
    manager.setCommand(relaxCommand)
    manager.manage()
}

enum class Result {
    SUCCESS,
    FAILURE,
    AVERAGE
}

class Manager(val person: Person) {
    private var command: ActionCommand? = null

    fun setCommand(command: ActionCommand) {
        this.command = command
    }

    fun manage() {
        println(command?.execute(person))
    }
}

interface ActionCommand {
    fun execute(person: Person): Result
    fun undo(person: Person)
}

class ActiveWorkCommand(private val coachService: CoachService): ActionCommand {
    override fun execute(person: Person): Result {
        return coachService.workAssistance(person)
    }

    override fun undo(person: Person) {
        coachService.relaxAssistance(person)
    }
}

class ActiveSportCommand(private val coachService: CoachService): ActionCommand {
    override fun execute(person: Person): Result {
        return coachService.sportAssistance(person)
    }

    override fun undo(person: Person) {
        coachService.relaxAssistance(person)
    }
}

class RelaxCommand(private val coachService: CoachService): ActionCommand {
    override fun execute(person: Person): Result {
        return coachService.relaxAssistance(person)
    }

    override fun undo(person: Person) {
        coachService.relaxAssistance(person)
    }
}

class CoachService {
    fun workAssistance(person: Person): Result {
        println("did active work")
        return Result.SUCCESS
    }
    fun sportAssistance(person: Person): Result {
        println("did active sport")
        return Result.AVERAGE
    }
    fun relaxAssistance(person: Person): Result {
        println("did relax")
        return Result.FAILURE
    }
}

data class Person(val name: String)