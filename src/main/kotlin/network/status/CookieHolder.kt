package network.status

object CookieHolder {
    var cookie: String = "null"
        set(value) {
            field = value
            get = true
        }

    var get = false

}