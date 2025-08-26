import okhttp3.*
import java.io.IOException

val client = OkHttpClient()

fun fetchLog(id: Int, callback: (String?, Throwable?) -> Unit) {
    val request = Request.Builder()
        .url("http://localhost:5000/logs/$id")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback(null, e)
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            callback(body, null)
        }
    })
}
