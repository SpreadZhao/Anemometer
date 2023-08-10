package network

import network.intercepter.CookieInterceptor
import network.intercepter.LoggingInterceptor
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val LOGIN_URL = "https://ids.xidian.edu.cn/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(LOGIN_URL)
//        .addConverterFactory(ToStringConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                .addInterceptor(CookieInterceptor())
                .build()
        )
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}
