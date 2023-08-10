package network.status

object CookieHolder {
    var cookie: String = ""
        set(value) {
            field = value
            get = true
        }

    var get = false

}