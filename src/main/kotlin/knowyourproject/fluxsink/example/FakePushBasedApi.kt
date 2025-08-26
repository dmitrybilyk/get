package knowyourproject.fluxsink.example

interface LogListener {
    fun onLog(log: String)
    fun onClose()
    fun onError(t: Throwable)
}

class LogService {
    fun register(listener: LogListener) {
        Thread {
            try {
                Thread.sleep(500)
                listener.onLog("First log")
                Thread.sleep(500)
                listener.onLog("Second log")
                Thread.sleep(500)
                listener.onLog("Third log")
                listener.onClose()
            } catch (e: InterruptedException) {
                listener.onError(e)
            }
        }.start()
    }
}
