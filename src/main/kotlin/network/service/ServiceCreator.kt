package network.service

import network.cookie.CookieManager
import network.intercepter.EhallRequestInterceptor
import network.intercepter.EhallResponseInterceptor
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

        private val cookieManager = CookieManager()

        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
            cookies.forEach { cookieManager.insertCookie(it) }
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            val path = parsePath(url.encodedPath())
            println("[发送Cookie]解析到的Path: $path")
            val res = cookieManager.fetchCookies(path)
            println("CookieJar要发送的Cookie: $res")
            return res
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
                .addInterceptor(EhallRequestInterceptor())
                .addInterceptor(EhallResponseInterceptor())
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
                .addInterceptor(EhallResponseInterceptor())
                .addInterceptor(EhallRequestInterceptor())
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
