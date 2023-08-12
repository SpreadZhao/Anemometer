package network.executor

import network.constant.TokenHolder
import network.executor.CustomService.await
import network.executor.CustomService.ehallService
import network.nestedrequest.QuickSoup
import org.jsoup.Jsoup

object EhallExecutor {

    private const val SERVICE_EHALL =
        "https://ehall.xidian.edu.cn/login?service=https://ehall.xidian.edu.cn/new/index.html"

    suspend fun login() {
        val response = ehallService.login(SERVICE_EHALL).await()
        if (response.code() !in 301 .. 302) {
            println("登录失败，返回码：${response.code()}")
            return
        }
        if (TokenHolder.ehallLocation == null || TokenHolder.ehallTempCookie == null) {
            println("ehallLocation或者ehallTempCookie为空，不能继续请求登录")
            return
        }
//        val response2 = QuickSoup.quickGet(TokenHolder.ehallLocation!!, TokenHolder.ehallTempCookie!!)
//        QuickSoup.quickGet(
//            url = "https://ehall.xidian.edu.cn/appShow",
//            allCookie = TokenHolder.ehallTempCookie ?: "",
//            params = mapOf(
//                "appId" to "4768574631264620"
//            )
//        )
    }
}