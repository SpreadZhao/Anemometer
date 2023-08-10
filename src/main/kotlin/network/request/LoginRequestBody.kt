package network.request

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    val username: String,
    val password: String,
    @SerializedName("rememberMe")
    val rememberMe: String,
    val cllt: String,
    val dllt: String,
    @SerializedName("_eventId")
    val eventId: String,
    val lt: String,
    val execution: String
)
