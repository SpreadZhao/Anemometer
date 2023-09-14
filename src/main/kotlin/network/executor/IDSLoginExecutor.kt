package network.executor

import network.executor.CommonService.await
import network.executor.CommonService.awaitAsString
import network.executor.CommonService.loginIDSService
import util.EncryptUtil

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
    ) {
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
        var response = loginIDSService.login(map).await()
        if (response.code() !in 301..302) {
            println("IDS 登录失败")
            return // LoginResponse(NetStatus.FAILURE, msg = "返回码：${response.code()}")
        }
        while (response.headers().values("Location").isNotEmpty()) {
            println("IDS 重定向中...")
            response = loginIDSService.redirectTo(response.headers().values("Location")[0]).await()
        }
    }


}