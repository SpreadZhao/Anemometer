package network.executor

import network.constant.AppStore
import network.constant.TokenHolder
import network.executor.CommonService.await
import network.executor.CommonService.ehallService
import network.executor.CommonService.loginIDSService

object EhallExecutor {

    private const val SERVICE_EHALL =
        "https://ehall.xidian.edu.cn/login?service=https://ehall.xidian.edu.cn/new/index.html"

    suspend fun loginToEhall() {
        var response = loginIDSService.loginTo(SERVICE_EHALL).await()
        if (response.code() !in 301 .. 302) {
            println("登录失败，返回码：${response.code()}")
            return
        }
        while (response.headers().values("Location").isNotEmpty()) {
            println("登录Ehall重定向中...")
            response = loginIDSService.redirectTo(response.headers().values("Location")[0]).await()
        }
//        if (TokenHolder.ehallLocation == null || TokenHolder.ehallTempCookie == null) {
//            println("ehallLocation或者ehallTempCookie为空，不能继续请求登录")
//            return
//        }
//        val response2 = QuickSoup.quickGet(TokenHolder.ehallLocation!!, TokenHolder.ehallTempCookie!!)
//        QuickSoup.quickGet(
//            url = "https://ehall.xidian.edu.cn/appShow",
//            allCookie = TokenHolder.ehallTempCookie ?: "",
//            params = mapOf(
//                "appId" to "4768574631264620"
//            )
//        )
    }

    suspend fun getScore() {
        enterApp(AppStore.GRADE_QUERY)?.let {
            println("enterApp location: $it")
            ehallService.simpleGet(it).await()
            val data = mapOf(
                "*json" to 1,
            )
        }
    }

    private suspend fun enterApp(appId: String): String? {
        val response = ehallService.enterApp(appId).await()
        val locations = response.headers().values("Location")
        if (locations.isNotEmpty()) {
            return locations[0]
        }
        return null
    }

    suspend fun isLoggedIn() {
        val response = ehallService.isLoggedIn().await()
        println("是否登录：${response.body()?.string()}")
    }
}