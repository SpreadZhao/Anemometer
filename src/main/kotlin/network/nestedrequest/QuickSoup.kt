package network.nestedrequest

import network.service.ServiceCreator.MOBILE_HEADER
import org.apache.http.client.CookieStore
import org.apache.http.cookie.Cookie
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.util.*

object QuickSoup {

    private val myCookieStore = object : CookieStore {
        override fun addCookie(cookie: Cookie?) {

        }

        override fun getCookies(): MutableList<Cookie> {
            TODO("Not yet implemented")
        }

        override fun clearExpired(date: Date?): Boolean {
            TODO("Not yet implemented")
        }

        override fun clear() {
            TODO("Not yet implemented")
        }
    }

    fun quickGet(url: String, allCookie: String, params: Map<String, String> = mapOf()): Connection.Response {
        val connection = Jsoup.connect(url)
            .header("User-Agent", MOBILE_HEADER)
            .header("cookie", allCookie)
            .ignoreContentType(true)
            .followRedirects(false)
            .method(Connection.Method.GET)
        if (params.isNotEmpty()) connection.data(params)
        println("Quick Soup请求信息：")
        println("请求 headers：${connection.request().headers()}")
        println("请求 url：${connection.request().url()}")
        val response = connection.execute()
        println("Quick Soup响应信息：")
        println("Code：${response.statusCode()}")
        println("headers: ${response.headers()}")
        println("cookies: ${response.cookies()}")
        println("body：${response.body()}")
        return response
    }
}