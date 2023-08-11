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
            for (i in cookies.indices) {
                if (i != cookies.lastIndex) {
                    res.append(cookies[i].substring(0, cookies[i].indexOf(";") + 1)).append(" ")
                }
                else {
                    res.append(cookies[i].substring(0, cookies[i].indexOf(";")))
                }
//                res.append("${cookie};")
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

    private fun writeCookie(cookies: List<String>) {

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