package network.cookie

import okhttp3.Cookie

class TrieNode(
    val route: String, // 路径上的值。比如/authserver/login
) {
    val children: MutableMap<String, TrieNode>
    var hasCookie = false
    val cookies: MutableList<Cookie>
    val name: String
    init {
        children = HashMap()
        cookies = mutableListOf()
        name = route.substring(route.lastIndexOf("/"))
    }

    override fun toString() = StringBuilder().run {
        appendLine("route: $route")
        appendLine("name: $name")
        appendLine("hasCookie: $hasCookie")
        if (hasCookie) appendLine("cookies: $cookies")
        toString()
    }
}