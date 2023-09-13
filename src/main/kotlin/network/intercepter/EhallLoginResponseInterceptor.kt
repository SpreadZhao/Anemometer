package network.intercepter

import network.constant.TokenHolder
import network.intercepter.InterceptorHandler.parseCookie
import okhttp3.Interceptor
import okhttp3.Response

class EhallLoginResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        println("Ehall Response拦截")
        val response = chain.proceed(chain.request())
        InterceptorHandler.printResponseInfo(response)
//        val cookies = response.headers("set-cookie")
        val location = response.headers("location")
//        writeCookies(cookies)
        writeLocation(location)
        return response
    }

    private fun writeCookies(cookies: List<String>) {
        val cookieStr = parseCookie(cookies)
        if (cookieStr == "" || cookies.isEmpty()) {
            println("cookie为空，不写入")
            return;
        }
        TokenHolder.ehallTempCookie = cookieStr
    }

    private fun writeLocation(location: List<String>) {
        if (location.size == 1) {
            if (TokenHolder.ehallLocation == null) {
                TokenHolder.ehallLocation = location[0]
            } else {
                println("已经有了location，不再写入")
            }
        }
    }
}