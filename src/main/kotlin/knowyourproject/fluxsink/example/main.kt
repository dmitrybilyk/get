package knowyourproject.fluxsink.example

import java.io.File

fun main() {
    val lines = mutableListOf<String>()
    File("data.txt").forEachLine { line ->
        lines.add(line)
    }

    // process
    for (line in lines) {
        println("Line: $line")
    }
}
