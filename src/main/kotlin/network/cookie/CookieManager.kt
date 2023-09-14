package network.cookie

import okhttp3.Cookie
import java.lang.StringBuilder

class CookieManager {

    private val trieRoot = TrieNode("/")

    fun insertCookie(cookie: Cookie) {
        val path = cookie.path()
        val list = parsePathToList(path)
        var p = trieRoot
        val currRouteBuilder = StringBuilder("/")
        for (currPath in list) {
            currRouteBuilder.append(currPath)
            if (!p.children.containsKey(currPath)) {
                p.children[currPath] = TrieNode(currRouteBuilder.toString())
            }
            p = p.children[currPath]!!
        }
        insertCookie(p.cookies, cookie)
        println("插入的Cookie信息：\n$p")
        p.hasCookie = true
    }

    fun fetchCookies(path: String): MutableList<Cookie> {
        val res = mutableListOf<Cookie>().apply {
            addAll(trieRoot.cookies)
        }
        val list = parsePathToList(path)
        println("fetchCookies 解析到的list：$list")
        var p = trieRoot
        for (currPath in list) {
            println("fetchCookies currPath: $currPath")
            if (!p.children.containsKey(currPath)) {
                println("获取Cookie失败！path: $path, currPath: $currPath")
                return res
            }
            p = p.children[currPath]!!
            println("fetchCookies hasCookie: ${p.hasCookie}")
            if (p.hasCookie) res.addAll(p.cookies)
        }
        return res
    }

    private fun parsePathToList(path: String): List<String> {
        val res = mutableListOf<String>()
        val builder = StringBuilder()
        var i = 1
        while (i < path.length) {
            val ch = path[i]
            if (ch != '/') {
                builder.append(ch)
            } else {
                res.add(builder.toString())
                builder.clear()
            }
            i++
        }
        if (builder.isNotEmpty()) res.add(builder.toString())
        return res
    }

    private fun insertCookie(list: MutableList<Cookie>, target: Cookie): Boolean {
        val index = findCookie(list, target)
        if (index != -1) {
            return updateCookie(list, target)
        }
        list.add(target)
        return containsCookie(list, target)
    }

    private fun findCookie(list: List<Cookie>, target: Cookie): Int {
        if (list.isEmpty()) return -1
        for ((i, cookie) in list.withIndex()) {
            if (cookie.name() == target.name()) return i
        }
        return -1
    }

    private fun containsCookie(list: List<Cookie>, target: Cookie) =
        findCookie(list, target) != -1

    private fun updateCookie(list: MutableList<Cookie>, target: Cookie): Boolean {
        val index = findCookie(list, target)
        if (index == -1) {
            println("没找到cookie")
            return false
        }
        list[index] = target
        return list[index] == target
    }
}