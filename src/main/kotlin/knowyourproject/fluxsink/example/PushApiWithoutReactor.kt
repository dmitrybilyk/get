package knowyourproject.fluxsink.example

fun main() {
    val logService = LogService()

    // manual listener implementation
    logService.register(object : LogListener {
        override fun onLog(log: String) {
            println("[Manual] Got log: $log")
        }

        override fun onClose() {
            println("[Manual] Logs completed")
        }

        override fun onError(t: Throwable) {
            println("[Manual] Error: ${t.message}")
        }
    })

    // block main thread so async logs can print
    Thread.sleep(2000)
}
