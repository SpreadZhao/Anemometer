package network.service

import network.intercepter.EhallLoginRequestInterceptor
import network.intercepter.EhallLoginResponseInterceptor
import network.intercepter.IDSLoginRequestInterceptor
import network.intercepter.IDSLoginResponseInterceptor
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder


object ServiceCreator {

    enum class RetrofitType {
        IDS_LOGIN,
        EHALL
    }

    private const val IDS_LOGIN_URL = "https://ids.xidian.edu.cn/"

    private const val EHALL_URL = "https://ehall.xidian.edu.cn/"

    const val MOBILE_HEADER =
        "Mozilla/5.0 (Linux; Android 11;WayDroid x86_64 Device Build/RQ3A.211001.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0Chrome/112.0.5615.136 Safari/537.36"

    private val myCookieJar = object : CookieJar {

        private val cookieMap = mutableMapOf<String, MutableList<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
            for (cookie in cookies) {
                printCookie(cookie)
                val path = cookie.path()
                var match = false
                val targetPathList = findAllMatchedPathOfCookieLists(path)
                if (targetPathList.isNotEmpty()) {
                    match = true
                    for (targetPath in targetPathList) {
                        insertCookie(cookieMap[targetPath]!!, cookie)
                    }
                }
                if (!match) cookieMap[path] = mutableListOf(cookie)
            }
            println("CookieJar写入Cookie后：${cookieMap}")
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            val path = parsePath(url.encodedPath())
            println("[发送Cookie]解析到的Path: $path")
            println("CookieJar要发送的Cookie: ${cookieMap[path]}")
            return if (cookieMap[path] == null) mutableListOf() else cookieMap[path]!!
        }

        private fun parsePath(oldPath: String): String {
            if (oldPath == "/" || oldPath.lastIndexOf("/") == 0) return oldPath
            val res = StringBuilder("/")
            for (i in 1 until oldPath.length) {
                val ch = oldPath[i]
                if (ch != '/') res.append(ch)
                else break
            }
            return res.toString()
        }

        private fun printCookie(cookie: Cookie) {
            println("Cookie信息：")
            println("Name: ${cookie.name()}")
            println("Domain: ${cookie.domain()}")
            println("Path: ${cookie.path()}")
            println("Value: ${cookie.value()}")
            println("Http: ${cookie.httpOnly()}")
            println("Expires at: ${cookie.expiresAt()}")
        }

        /**
         * Return index of a cookie when found it.
         * -1 means not found.
         */
        private fun findCookie(list: List<Cookie>, target: Cookie): Int {
            if (list.isEmpty()) return -1
            for ((i, cookie) in list.withIndex()) {
                if (cookie.name() == target.name()) return i
            }
            return -1
        }

        private fun containsCookie(list: List<Cookie>, target: Cookie) =
            findCookie(list, target) != -1

        private fun updateCookie(list: MutableList<Cookie>, target: Cookie): Boolean {
            val index = findCookie(list, target)
            if (index == -1) {
                println("没找到cookie")
                return false
            }
            list[index] = target
            return list[index] == target
        }

        private fun insertCookie(list: MutableList<Cookie>, target: Cookie): Boolean {
            val index = findCookie(list, target)
            if (index != -1) {
                return updateCookie(list, target)
            }
            list.add(target)
            return containsCookie(list, target)
        }

        private fun findAllMatchedPathOfCookieLists(path: String): List<String> {
            val res = mutableListOf<String>()
            for (entry in cookieMap) {
                if (entry.key.contains(path)) {
                    res.add(entry.key)
                }
            }
            return res
        }
    }

    private val retrofitIDSLogin = Retrofit.Builder()
        .baseUrl(IDS_LOGIN_URL)
//        .addConverterFactory(ToStringConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .cookieJar(myCookieJar)
                .addInterceptor(IDSLoginRequestInterceptor())
                .addInterceptor(IDSLoginResponseInterceptor())
                .followRedirects(false)
                .build()
        )
        .build()

    private val retrofitEhallTest = Retrofit.Builder()
        .baseUrl(IDS_LOGIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .cookieJar(myCookieJar)
                .addInterceptor(EhallLoginRequestInterceptor())
                .addInterceptor(EhallLoginResponseInterceptor())
                .followRedirects(false)
                .build()
        )
        .build()

    private val retrofitEhall = Retrofit.Builder()
        .baseUrl(EHALL_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .cookieJar(myCookieJar)
                .addInterceptor(EhallLoginResponseInterceptor())
                .addInterceptor(EhallLoginRequestInterceptor())
                .followRedirects(false)
                .build()
        )
        .build()




    fun <T> create(type: RetrofitType, serviceClass: Class<T>): T = when (type) {
        RetrofitType.IDS_LOGIN -> retrofitIDSLogin.create(serviceClass)
        RetrofitType.EHALL -> retrofitEhall.create(serviceClass)
    }

//    inline fun <reified T> create(type: RetrofitType): T = when (type) {
//        RetrofitType.IDS_LOGIN -> create(retrofitIDSLogin, T::class.java)
//        RetrofitType.EHALL -> create()
//    }

}
