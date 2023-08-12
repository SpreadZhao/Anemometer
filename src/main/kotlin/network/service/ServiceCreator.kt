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

        private var list = mutableListOf<Cookie>()

        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
            list = cookies
            println("CookieJar写入了Cookie: $list")
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            println("CookieJar要读取Cookie：$list")
            return list
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
        RetrofitType.EHALL -> retrofitEhallTest.create(serviceClass)
    }

//    inline fun <reified T> create(type: RetrofitType): T = when (type) {
//        RetrofitType.IDS_LOGIN -> create(retrofitIDSLogin, T::class.java)
//        RetrofitType.EHALL -> create()
//    }

}
