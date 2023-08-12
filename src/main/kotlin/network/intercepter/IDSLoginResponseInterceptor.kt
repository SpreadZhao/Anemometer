package network.intercepter

import network.constant.TokenHolder
import network.intercepter.InterceptorHandler.parseCookie
import okhttp3.Interceptor
import okhttp3.Response

class IDSLoginResponseInterceptor : Interceptor {

    @Volatile
    var longTimeCookie = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        InterceptorHandler.printResponseInfo(response)
        val cookies = response.headers("set-cookie")
        val location = response.headers("location")
        writeLocation(location)
//        writeCookie(cookies)
        return response
    }

    fun writeCookie(cookies: List<String>) {
        val cookieStr = parseCookie(cookies)
        if (cookieStr == "" || cookies.isEmpty()) {
            println("Cookie为空，不写入")
            return
        }
        if (longTimeCookie) {
            println("第三次post请求的cookie，必须写入")
            TokenHolder.idsPostCookie = cookieStr
            TokenHolder.ehallTempCookie = cookieStr
            longTimeCookie = false
            return
        }
        if (TokenHolder.tempIDSCookie == null) {
            TokenHolder.tempIDSCookie = cookieStr
            println("写入cookie成功")
        } else {
            println("已经有了cookie，不再写入")
        }
    }

    private fun writeLocation(location: List<String>) {
        if (location.size == 1) {
            if (TokenHolder.idsLocation == null) {
                TokenHolder.idsLocation = location[0]
                longTimeCookie = true
            } else {
                println("已经有了location，不再写入")
            }
        }
    }
}