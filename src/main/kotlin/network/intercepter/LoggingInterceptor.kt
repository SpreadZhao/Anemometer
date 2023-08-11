package network.intercepter

import network.status.CookieHolder
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Headers
import util.PrintUtil
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
        println("请求信息：${chain.request().method()}")

        val cookie = CookieHolder.cookie
        val request = chain.request().newBuilder().apply {
            addHeader("Content-Type", "application/x-www-form-urlencoded")
            addHeader(
                "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.200"
            )
            .addHeader("Cookie", cookie)
                .addHeader("Connection", "keep-alive")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Accept-Encoding", "utf8")
                .addHeader("Accept-Language", "en-GB,en;q=0.9,en-US;q=0.8,zh-CN;q=0.7,zh;q=0.6")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-")
            if (chain.request().body() != null) {
                addHeader("Content-Length", "${chain.request().body()!!.contentLength()}")
            }
        }.build()
        println("Headers: ${request.headers()}")
        println("Request Body Length: ${request.body()?.contentLength()}")
        println("Request Body Type: ${request.body()?.contentType()}")
        println("Request Body: ${request.body()?.let { PrintUtil.getBodyContent(it) }}")
        println("Request URL: ${request.url()}")
        return chain.proceed(request);
    }
}