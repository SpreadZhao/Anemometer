package network.cookie

import okhttp3.Cookie
import java.io.StringWriter
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter

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

    fun toXmlString(): String {
        val sb = StringBuilder()
        sb.append("<TrieNode route=\"$route\" name=\"$name\" hasCookie=\"$hasCookie\">\n")

        if (hasCookie) {
            sb.append("<Cookies>\n")
            cookies.forEach {
                sb.append("<Cookie>$it</Cookie>\n")
            }
            sb.append("</Cookies>\n")
        }

        children.forEach { (key, node) ->
            sb.append("<Child key=\"$key\">\n")
            sb.append(node.toXmlString())
            sb.append("</Child>\n")
        }

        sb.append("</TrieNode>\n")

        return sb.toString()
    }
}