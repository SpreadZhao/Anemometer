package network.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface LoginService {

    @GET("authserver/login")
    fun getLoginPage(): Call<ResponseBody>

    @GET("authserver/checkNeedCaptcha.htl")
    fun checkNeedCaptcha(@Query("username") username: String, @Query("_") time: String): Call<ResponseBody>

    // https://github.com/square/retrofit/issues/3275
    @POST("authserver/login")
    @FormUrlEncoded
    @JvmSuppressWildcards
    fun login(@FieldMap attrs: Map<String, String>): Call<ResponseBody>

    @GET("authserver/login")
    fun loginTo(@Query("service") service: String): Call<ResponseBody>

    @GET
    fun redirectTo(@Url location: String): Call<ResponseBody>

}