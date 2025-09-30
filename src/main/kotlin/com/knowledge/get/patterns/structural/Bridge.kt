package com.knowledge.get.patterns.structural

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    // Create loggers with different outputs
    val simpleConsoleLogger = SimpleLogger(ConsoleOutput())
    val detailedFileLogger = DetailedLogger(FileOutput())

    // Log messages
    println("=== Simple Logger to Console ===")
    simpleConsoleLogger.log("User logged in")
    simpleConsoleLogger.logError("Authentication failed")

    println("\n=== Detailed Logger to File ===")
    detailedFileLogger.log("Database connection established")
    detailedFileLogger.logError("Query execution failed")
}

// Abstraction: Defines the high-level logging interface
abstract class Logger(protected val output: LogOutput) {
    abstract fun log(message: String)
    abstract fun logError(message: String)
}

// RefinedAbstraction: Specific logger formats
class SimpleLogger(output: LogOutput) : Logger(output) {
    override fun log(message: String) {
        val formattedMessage = "INFO: $message"
        output.write(formattedMessage)
    }

    override fun logError(message: String) {
        val formattedMessage = "ERROR: $message"
        output.write(formattedMessage)
    }
}

class DetailedLogger(output: LogOutput) : Logger(output) {
    private val timestamp: String
        get() = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    override fun log(message: String) {
        val formattedMessage = "[$timestamp] INFO: $message"
        output.write(formattedMessage)
    }

    override fun logError(message: String) {
        val formattedMessage = "[$timestamp] ERROR: $message (Severity: High)"
        output.write(formattedMessage)
    }
}

// Implementor: Defines the interface for log output destinations
interface LogOutput {
    fun write(message: String)
}

// ConcreteImplementor: Specific output destinations
class ConsoleOutput : LogOutput {
    override fun write(message: String) {
        println("Console: $message")
    }
}

class FileOutput : LogOutput {
    override fun write(message: String) {
        println("File: Writing '$message' to log file")
    }
}