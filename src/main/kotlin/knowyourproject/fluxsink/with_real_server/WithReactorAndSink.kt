package knowyourproject.fluxsink.with_real_server

import fetchLog
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

fun fetchLogMono(id: Int): Mono<String> {
    return Mono.create { sink ->
        fetchLog(id) { data, error ->
            if (error != null) sink.error(error)
            else sink.success(data!!)
        }
    }
}

fun main() {
    fetchLogMono(1)
        .flatMap { log1 ->
            fetchLogMono(2).map { log2 -> "$log1 + $log2" }
        }
        .subscribe(
            { result -> println("Combined result: $result") },
            { error -> println("Error: ${error.message}") }
        )

    Thread.sleep(6000)
}

