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

@Composable
@Preview
fun App() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
                Button(onClick = {
                    coroutineScope.launch {
                        val page = AnemoNetwork.getLoginPage()
                        val doc = Jsoup.parse(page)
                        val hiddenForms = doc.select("input[type=hidden]")
                        val key = hiddenForms.select("input#pwdEncryptSalt").first()?.`val`()
                        val lt = hiddenForms.select("input#lt").first()?.`val`()
                        val execution = hiddenForms.select("input#execution").first()?.`val`()
                        val res = AnemoNetwork.login(username, password, key ?: "", lt ?: "", execution ?: "")
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
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
