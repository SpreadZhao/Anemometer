package network.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EhallService {
    @GET("authserver/login")
    fun login(@Query("service") service: String): Call<ResponseBody>

    @GET("jsonp/userFavoriteApps.json")
    fun isLoggedIn(): Call<ResponseBody>

    @GET("appShow")
    @Headers("Accept: text/html,application/xhtml+xml,application/xml; q=0.9,image/webp,image/apng,*/*;q=0.8")
    fun enterApp(@Query("appId") appId: String): Call<ResponseBody>
}