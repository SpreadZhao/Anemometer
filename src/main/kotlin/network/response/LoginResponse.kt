package network.response

import network.status.NetStatus

data class LoginResponse(
    var status: NetStatus,
    val code: Int = -1,
    val msg: String? = null
)
