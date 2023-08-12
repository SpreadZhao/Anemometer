package network.nestedrequest

import network.service.ServiceCreator.MOBILE_HEADER
import org.jsoup.Connection
import org.jsoup.Jsoup

object QuickSoup {
    fun quickGet(url: String, allCookie: String): Connection.Response {
        val connection = Jsoup.connect(url)
            .header("User-Agent", MOBILE_HEADER)
            .header("cookie", allCookie)
            .ignoreContentType(true)
            .followRedirects(false)
            .method(Connection.Method.GET)
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