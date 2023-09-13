package network.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface EhallService {
    @GET("authserver/login")
    fun login(@Query("service") service: String): Call<ResponseBody>

    @GET("jsonp/userFavoriteApps.json")
    fun isLoggedIn(): Call<ResponseBody>

    @GET("appShow")
    @Headers("Accept: text/html,application/xhtml+xml,application/xml; q=0.9,image/webp,image/apng,*/*;q=0.8")
    fun enterApp(@Query("appId") appId: String): Call<ResponseBody>

    @GET
    fun simpleGet(@Url url: String): Call<ResponseBody>

    // 学生成绩查询
    @POST("jwapp/sys/cjcx/modules/cjcx/xscjcx.do")
    @FormUrlEncoded
    @JvmSuppressWildcards
    fun xscjcx(@FieldMap data: Map<String, Any>): Call<ResponseBody>
}