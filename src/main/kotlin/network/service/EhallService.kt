package network.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EhallService {
    @GET("authserver/login")
    fun login(@Query("service") service: String): Call<ResponseBody>
}