package network.intercepter

import network.status.CookieHolder
import okhttp3.Interceptor
import okhttp3.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter

class CookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        println("响应信息：")
        println("response headers: ${response.headers()}")
        val cookies = response.headers("set-cookie")
        cookies.let {
            println("接收到的cookie: $it")
            val res = StringBuilder()
            for (cookie in cookies) {
                if (cookie.contains("route") || cookie.contains("JSESSIONID")) {
                    res.append(cookie.substring(0, cookie.indexOf(";") + 1))
                }
            }
//            val cookieFile = File("CookieFile")
//            val writer = FileWriter(cookieFile, false)
            println("写入的cookie: $res")
//            writer.write(res.toString())
//            writer.flush()
//            writer.close()
            if (!CookieHolder.get) CookieHolder.cookie = res.toString()
        }
        return response
    }
}


//            val cookieFile = File("CookieFile")
//            var input = it
//            try {
//                val reader = FileReader(cookieFile)
//                input += ";${reader.readText()}"
//                reader.close()
//            } catch (e: FileNotFoundException) {
//                println("第一次写入cookie")
//            }
//            println("写入的cookie: $input")
//            val writer = FileWriter(cookieFile, false)
//            writer.write(input)
//            writer.flush()
//            writer.close()