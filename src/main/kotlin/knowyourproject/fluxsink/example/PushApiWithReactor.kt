package knowyourproject.fluxsink.example

import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

fun main() {
    val logService = LogService()

    val logFlux: Flux<String> = Flux.create { sink: FluxSink<String> ->
        logService.register(object : LogListener {
            override fun onLog(log: String) {
                sink.next(log)   // push event
            }

            override fun onClose() {
                sink.complete()  // signal completion
            }

            override fun onError(t: Throwable) {
                sink.error(t)    // signal error
            }
        })
    }

    // Now use reactive operators
    logFlux
        .map { "[Processed] $it" }
        .subscribe { println(it) }
}
