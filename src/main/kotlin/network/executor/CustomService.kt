package network.executor

import network.service.EhallService
import network.service.LoginService
import network.service.ServiceCreator
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CustomService {
    val loginIDSService = ServiceCreator.create(
        ServiceCreator.RetrofitType.IDS_LOGIN,
        LoginService::class.java
    )
    val ehallService = ServiceCreator.create(
        ServiceCreator.RetrofitType.EHALL,
        EhallService::class.java
    )

    suspend fun <T> Call<T>.await(): Response<T> {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun <T> Call<T>.awaitAsString(): String {
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