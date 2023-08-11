package network

import network.request.LoginRequestBody
import network.response.CaptchaResponse
import network.response.LoginResponse
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

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

}