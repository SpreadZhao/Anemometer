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
import network.constant.NetStatus
import util.EncryptUtil

@Composable
@Preview
fun TestScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var test by remember { mutableStateOf("") }
    var testRes by remember { mutableStateOf("") }
    var testKey by remember { mutableStateOf("") }
    var loginState by remember { mutableStateOf("") }
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
                        val res = AnemoNetwork.firstTimeLogin(username, password)
                        when (res.status) {
                            NetStatus.OK -> {
                                loginState = "登录成功"
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
                Button(onClick = {
                    coroutineScope.launch {
                        println("登录到Ehall")
                        val response = AnemoNetwork.loginToEhall()
                    }
                }) {
                    Text(text = "登录Ehall")
                }
                Text(text = testRes)
                Text(text = loginState)
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        TestScreen()
    }
}
