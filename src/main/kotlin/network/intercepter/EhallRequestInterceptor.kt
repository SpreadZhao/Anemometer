package network.intercepter

import network.intercepter.InterceptorHandler.addBaseHeaders
import okhttp3.Interceptor
import okhttp3.Response

class EhallRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("Ehall Login Request拦截")
//        val cookie = TokenHolder.ehallTempCookie ?: ""
        val request = chain.request().newBuilder()
            .addBaseHeaders(chain.request())
//            .addHeader("Cookie", cookie)
            .build()
        InterceptorHandler.printRequestInfo(request)
        return chain.proceed(request)
    }
}