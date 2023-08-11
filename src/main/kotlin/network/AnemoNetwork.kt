package network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import network.request.LoginRequestBody
import network.response.CaptchaResponse
import network.response.LoginResponse
import network.status.NetStatus
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.EncryptUtil
import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AnemoNetwork {
    private val loginService = ServiceCreator.create<LoginService>()

    suspend fun getLoginPage() = loginService.getLoginPage().awaitAsString()

    suspend fun checkNeedCaptcha(username: String) = run {
        val currTime = System.currentTimeMillis().toString()
        loginService.checkNeedCaptcha(username, currTime).awaitAsString()
    }

    suspend fun login(username: String, password: String, key: String, lt: String, execution: String): LoginResponse {
//        val body = LoginRequestBody(
//            username,
//            EncryptUtil.aesEncrypt(password, key),
//            "",
//            "true",
//            "userNameLogin",
//            "generalLogin",
//            "submit",
//            lt,
//            execution
//        )
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
//        println("body: ${gson.toJson(body)}")
        return suspendCoroutine { continuation ->
            loginService.login(map).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    println("状态码: ${response.code()}")
                    when (response.code()) {
                        401 -> continuation.resume(LoginResponse(NetStatus.WRONG_PASSWORD))
                        301, 302 -> continuation.resume(LoginResponse(NetStatus.OK))
                        else -> continuation.resume(LoginResponse(NetStatus.FAILURE, response.code(), response.body()?.string() ?: ""))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    println("状态码: ${response.code()}")
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("Body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private suspend fun <T> Call<T>.awaitAsString(): String {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    println("状态码: ${response.code()}")
                    val body = response.body()
                    if (body != null && body is ResponseBody) {
                        continuation.resume(body.string())
                    }
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}