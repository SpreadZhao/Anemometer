import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import network.AnemoNetwork
import network.status.NetStatus
import org.jsoup.Jsoup
import util.EncryptUtil
import java.io.BufferedReader

@Composable
@Preview
fun App() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var test by remember { mutableStateOf("") }
    var testRes by remember { mutableStateOf("") }
    var testKey by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    MaterialTheme {
        LazyColumn {
            item {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "User Name") }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") }
                )
                OutlinedTextField(
                    value = test,
                    onValueChange = { test = it },
                    label = { Text(text = "原文") }
                )
                OutlinedTextField(
                    value = testKey,
                    onValueChange = { testKey = it },
                    label = { Text(text = "密钥") }
                )
                Button(onClick = {
                    coroutineScope.launch {
                        println("发送第一次请求")
                        repeat(5) {
                            AnemoNetwork.getLoginPage()
                        }
                        println("发送第二次请求")
                        val page = AnemoNetwork.getLoginPage()
                        println("第一次请求表单：$page")
                        println("发送第三次请求")
                        val needCaptcha = AnemoNetwork.checkNeedCaptcha(username)
                        println("need: $needCaptcha")
                        val doc = Jsoup.parse(page)
//                        println("Body: ${doc.body()}")
                        val hiddenForms = doc.select("input[type=hidden]")
                        val key = hiddenForms.select("input#pwdEncryptSalt").first()?.`val`()
                        val lt = hiddenForms.select("input#lt").first()?.`val`()
                        val execution = hiddenForms.select("[name=execution]").first()?.`val`()
                        println("key: $key")
                        println("lt: $lt")
                        println("execution: $execution")
                        println("发送第四次请求")
                        val res = AnemoNetwork.login(username, password, key ?: "", lt ?: "", execution ?: "")
                        println("code: ${res.code}")
                        when (res.status) {
                            NetStatus.OK -> {
                                println("Login success")
                            }
                            NetStatus.WRONG_PASSWORD -> {
                                println("Wrong password")
                            }
                            else -> {
                                println("Login failure: ${res.msg}")
                            }
                        }
                    }
                }) {
                    Text(text = "login")
                }
                Button(onClick = {
                    testRes = EncryptUtil.aesEncrypt(test, testKey)
                }) {
                    Text(text = "加密")
                }
                Text(text = testRes)
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
