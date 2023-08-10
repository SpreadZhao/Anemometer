package network.response

import network.status.NetStatus

data class LoginResponse(
    var status: NetStatus,
    val msg: String? = null
)
