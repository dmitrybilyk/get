package knowyourproject.fluxsink

import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    simpleJust()
    simpleGenerate()
    simpleCreate()
    simplePush()
}

// 1. Static Flux with known data
fun simpleJust() {
    println("\n=== Flux.just ===")
    Flux.just("A", "B", "C")
        .subscribe { value -> println("Received: $value") }
}

// 2. Programmatic synchronous generator
fun simpleGenerate() {
    println("\n=== Flux.generate ===")
    val state = AtomicInteger(1)

    Flux.generate<String> { sink ->
        val i = state.getAndIncrement()
        sink.next("Value $i")
        if (i == 3) sink.complete()
    }.subscribe { println(it) }
}

// 3. Asynchronous with Flux.create (using FluxSink)
fun simpleCreate() {
    println("\n=== Flux.create (with FluxSink) ===")

    val flux = Flux.create<String>({ emitter ->
        Thread {
            repeat(5) { i ->
                emitter.next("Event ${i + 1}")
                Thread.sleep(300)
            }
            emitter.complete()
        }.start()
    }, FluxSink.OverflowStrategy.BUFFER)

    flux.subscribe { v -> println("Subscriber got: $v") }

    Thread.sleep(2000) // wait for background thread
}

// 4. Flux.push – similar but optimized for single-threaded sources
fun simplePush() {
    println("\n=== Flux.push ===")

    val flux = Flux.push<String> { emitter ->
        Thread {
            repeat(5) { i ->
                emitter.next("Push ${i + 1}")
                Thread.sleep(200)
            }
            emitter.complete()
        }.start()
    }

    flux.subscribe { v -> println("Subscriber got: $v") }

    Thread.sleep(1500)
}
