package network.intercepter

import network.status.CookieHolder
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Headers
import java.io.FileNotFoundException
import java.io.FileReader

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        var cookie = ""
//        try {
//            cookie = FileReader("CookieFile").run { readText() }
//        } catch (e: FileNotFoundException) {
//            println("First time login")
//        }
        println("请求信息：")
        val cookie = CookieHolder.cookie
        val request = chain.request().newBuilder()
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader(
                "user-agent","Mozilla/5.0 (Linux; Android 11;" +
                        "WayDroid x86_64 Device Build/RQ3A.211001.001; wv)" +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0" +
                        "Chrome/112.0.5615.136 Safari/537.36"
            )
            .addHeader("cookie", cookie)
            .addHeader("content-length", (if (chain.request().body() != null) {
                chain.request().body().toString().length
            } else 0).toString())
            .build()
        println("Headers: ${request.headers()}")
        println("Request Body: ${request.body()}")
        return chain.proceed(request);
    }
}