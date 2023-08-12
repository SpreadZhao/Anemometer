package network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import network.executor.EhallExecutor
import network.executor.IDSLoginExecutor
import org.jsoup.Jsoup

object AnemoNetwork {
    suspend fun firstTimeLogin(username: String, password: String) = withContext(Dispatchers.IO) {
        val page = IDSLoginExecutor.getLoginPage()
        val needCaptcha = IDSLoginExecutor.checkNeedCaptcha(username)
        val doc = Jsoup.parse(page)
        val hiddenForms = doc.select("input[type=hidden]")
        val key = hiddenForms.select("input#pwdEncryptSalt").first()?.`val`()
        val lt = hiddenForms.select("input#lt").first()?.`val`()
        val execution = hiddenForms.select("[name=execution]").first()?.`val`()
        IDSLoginExecutor.login(username, password, key ?: "", lt ?: "", execution ?: "")
    }

    suspend fun loginToEhall() = withContext(Dispatchers.IO) {
        EhallExecutor.login()
    }

}