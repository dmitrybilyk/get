package knowyourproject.fluxsink.with_real_server

import fetchLog

fun main() {
    fetchLog(1) { log1, err1 ->
        if (err1 != null) {
            println("Error fetching log1: ${err1.message}")
        } else {
            println("Got log1: $log1")

            // Now fetch log2 only after log1
            fetchLog(2) { log2, err2 ->
                if (err2 != null) {
                    println("Error fetching log2: ${err2.message}")
                } else {
                    println("Got log2: $log2")

                    // Combine results manually
                    val combined = "$log1 + $log2"
                    println("Combined: $combined")
                }
            }
        }
    }

    Thread.sleep(6000) // keep app alive
}
