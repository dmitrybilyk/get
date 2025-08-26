package knowyourproject

import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.Duration

class FluxSinkPlayground {

    fun simpleExample() {
        val flux = Flux.create { sink: FluxSink<String> ->
            sink.next("Hello")
            sink.next("from")
            sink.next("FluxSink")
            sink.complete()
        }

        flux.subscribe(
            { println("onNext: $it") },
            { println("onError: ${it.message}") },
            { println("onComplete") }
        )
    }

    fun periodicExample() {
        val flux = Flux.create { sink: FluxSink<Long> ->
            var counter = 0L
            val thread = Thread {
                try {
                    while (counter < 5) {
                        sink.next(counter++)
                        Thread.sleep(500) // simulate external events
                    }
                    sink.complete()
                } catch (e: Exception) {
                    sink.error(e)
                }
            }
            thread.start()
        }

        flux.subscribe(
            { println("onNext: $it") },
            { println("onError: ${it.message}") },
            { println("onComplete") }
        )
    }
}

fun main() {
    val playground = FluxSinkPlayground()
    println("=== Simple Example ===")
    playground.simpleExample()

    println("\n=== Periodic Example ===")
    playground.periodicExample()

    Thread.sleep(4000) // wait for async events to complete
}
