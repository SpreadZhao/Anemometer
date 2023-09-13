package network.cookie

import okhttp3.Cookie

class TrieNode(
    route: String, // 路径上的值。比如/authserver/login
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
}