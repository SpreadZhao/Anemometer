package network

import okhttp3.Interceptor
import okhttp3.Response

class LoggingIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        println("Headers: ${request.headers()}")
        return chain.proceed(request);
    }
}