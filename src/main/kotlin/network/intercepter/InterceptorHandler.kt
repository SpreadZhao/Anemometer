package network.intercepter

import network.service.ServiceCreator
import okhttp3.Request
import okhttp3.Response
import util.PrintUtil

object InterceptorHandler {
    fun printRequestInfo(request: Request) {
        println("Request信息")
        println("Method: ${request.method()}")
        println("Headers: ${request.headers()}")
        println("Cookies: ${request.headers("cookie")}")
        println("Request Body Length: ${request.body()?.contentLength()}")
        println("Request Body Type: ${request.body()?.contentType()}")
        println("Request Body: ${request.body()?.let { PrintUtil.getBodyContent(it) }}")
        println("Request URL: ${request.url()}")
    }

    fun printResponseInfo(response: Response) {
        println("Response信息")
        println("Code: ${response.code()}")
        println("Msg: ${response.message()}")
        println("Headers: ${response.headers()}")
        println("Cookies: ${response.headers("set-cookie")}")
        println("has body: ${response.body() != null}")
    }

    fun Request.Builder.addBaseHeaders(oldRequest: Request) = apply {
        addHeader("Content-Type", "application/x-www-form-urlencoded")
        addHeader("User-Agent", ServiceCreator.MOBILE_HEADER)
        if (oldRequest.body() != null) {
            addHeader("Content-Length", "${oldRequest.body()!!.contentLength()}")
        }
    }

    fun parseCookie(cookies: List<String>): String {
        val res = StringBuilder()
        for (i in cookies.indices) {
            if (i != cookies.lastIndex) {
                res.append(cookies[i].substring(0, cookies[i].indexOf(";") + 1)).append(" ")
            } else {
                res.append(cookies[i].substring(0, cookies[i].indexOf(";")))
            }
        }
        println("待写入的Cookie: $res")
        return res.toString()
    }
}