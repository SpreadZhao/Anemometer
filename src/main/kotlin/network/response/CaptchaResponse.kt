package network.response

import com.google.gson.annotations.SerializedName

data class CaptchaResponse(
    @SerializedName("isNeed")
    val isNeed: Boolean
)
