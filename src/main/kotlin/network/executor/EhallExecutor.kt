package network.executor

import network.executor.CustomService.await
import network.executor.CustomService.ehallService

object EhallExecutor {

    private const val SERVICE_EHALL =
        "https://ehall.xidian.edu.cn/login?service=https://ehall.xidian.edu.cn/new/index.html"

    suspend fun login() = ehallService.login(SERVICE_EHALL).await()
}