package network.executor

import com.google.gson.GsonBuilder
import network.constant.NetStatus
import network.executor.CustomService.awaitAsString
import network.executor.CustomService.loginIDSService
import network.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.EncryptUtil
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object IDSLoginExecutor {

    suspend fun getLoginPage() = loginIDSService.getLoginPage().awaitAsString()

    suspend fun checkNeedCaptcha(username: String) = run {
        val currTime = System.currentTimeMillis().toString()
        loginIDSService.checkNeedCaptcha(username, currTime).awaitAsString()
    }

    suspend fun login(
        username: String,
        password: String,
        key: String,
        lt: String,
        execution: String
    ): LoginResponse {
        val map = mapOf(
            "username" to username,
            "password" to EncryptUtil.aesEncrypt(password, key),
            "captcha" to "",
            "rememberMe" to "true",
            "cllt" to "userNameLogin",
            "dllt" to "generalLogin",
            "_eventId" to "submit",
            "lt" to "",
            "execution" to execution
        )
        // https://www.cnblogs.com/Anidot/p/9266817.html
        val gson = GsonBuilder().disableHtmlEscaping().create()
        return suspendCoroutine { continuation ->
            loginIDSService.login(map).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    println("状态码: ${response.code()}")
                    when (response.code()) {
                        401 -> continuation.resume(LoginResponse(NetStatus.WRONG_PASSWORD, msg = "密码错误"))
                        301, 302 -> continuation.resume(LoginResponse(NetStatus.OK, response.code()))
                        else -> continuation.resume(
                            LoginResponse(
                                NetStatus.FAILURE,
                                response.code(),
                                response.body()?.string() ?: ""
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }


}