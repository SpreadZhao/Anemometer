package test

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import network.AnemoNetwork
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
                        AnemoNetwork.loginToEhall()
                    }
                }) {
                    Text(text = "登录Ehall")
                }
                Button(onClick = {
                    coroutineScope.launch {
                        println("检查是否登录")
                        AnemoNetwork.isLoggedIn()
                    }
                }) {
                    Text("检查是否登录")
                }
                Button(onClick = {
                    coroutineScope.launch {
                        println("进入成绩查询应用")
                        AnemoNetwork.getScore()
                    }
                }) {
                    Text("成绩查询")
                }
                Text(text = testRes)
                Text(text = loginState)
            }
        }
    }
}
