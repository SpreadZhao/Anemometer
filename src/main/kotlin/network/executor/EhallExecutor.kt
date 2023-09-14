package network.executor

import network.constant.AppStore
import network.executor.CommonService.await
import network.executor.CommonService.ehallService
import network.executor.CommonService.loginIDSService
import util.GsonUtil

object EhallExecutor {

    private const val SERVICE_EHALL =
        "https://ehall.xidian.edu.cn/login?service=https://ehall.xidian.edu.cn/new/index.html"

    suspend fun loginToEhall() {
        var response = loginIDSService.loginTo(SERVICE_EHALL).await()
        if (response.code() !in 301..302) {
            println("登录失败，返回码：${response.code()}")
            return
        }
        while (response.headers().values("Location").isNotEmpty()) {
            println("登录Ehall重定向中...")
            response = loginIDSService.redirectTo(response.headers().values("Location")[0]).await()
        }
    }

    suspend fun getScore() {
        enterApp(AppStore.GRADE_QUERY)?.let {
            println("enterApp location: $it")
            ehallService.simpleGet(it).await()
            val querySetting = mapOf(
                "name" to "SFYX",
                "value" to "1",
                "linkOpt" to "and",
                "builder" to "m_value_equal"
            )
            val data = mapOf(
                "*json" to 1,
                "querySetting" to GsonUtil.toJsonString(querySetting),
                "*order" to "+XNXQDM,KCH,KXH",
                "pageSize" to 1000,
                "pageNumber" to 1
            )
            val response = ehallService.xscjcx(data).await()
            println("学生成绩查询：\n${response.body()?.string()}")
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