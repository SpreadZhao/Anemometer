package network.intercepter

import network.intercepter.InterceptorHandler.addBaseHeaders
import okhttp3.Interceptor
import okhttp3.Response

class IDSLoginRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("IDS Request拦截")
//        val cookie = TokenHolder.tempIDSCookie ?: ""
        val request = chain.request().newBuilder()
            .addBaseHeaders(chain.request())
//            .addHeader("Cookie", cookie)
            .build()
        InterceptorHandler.printRequestInfo(request)
        return chain.proceed(request)
    }
}