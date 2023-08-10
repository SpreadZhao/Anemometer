package network

import network.request.LoginRequestBody
import network.response.LoginResponse
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LoginService {

    @GET("authserver/login")
    fun getLoginPage(): Call<ResponseBody>


    // https://github.com/square/retrofit/issues/3275
    @Headers(
        "user-agent: Mozilla/5.0 (Linux; Android 11;" +
                "WayDroid x86_64 Device Build/RQ3A.211001.001; wv)" +
                "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0" +
                "Chrome/112.0.5615.136 Safari/537.36"
    )
    @POST("authserver/login")
    @JvmSuppressWildcards
    fun login(@Body attrs: LoginRequestBody): Call<ResponseBody>

}