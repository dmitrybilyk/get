package knowyourproject.fluxsink.example

import reactor.core.publisher.Flux
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Paths.get("data.txt")

    val flux: Flux<String> = Flux.fromStream { Files.lines(path) }

    flux.subscribe { line -> println("Line: $line") }
}
