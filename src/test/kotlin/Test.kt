import network.cookie.TrieNode
import okhttp3.Cookie

fun main() {

//    println(parsePathToList("/auth/abc/def"))
    testTrieToXML()

}

private fun testTrieToXML() {
    val root = TrieNode("/")
    root.children["spread"] = TrieNode("/spread")
    root.hasCookie = true
    root.cookies.add(Cookie.Builder().name("haha").value("hehe").domain("example.com").path("/").build())
    root.children["zhao"] = TrieNode("/zhao")
    root.children["zhao"]!!.hasCookie = true
    root.children["zhao"]!!.cookies.add(Cookie.Builder().name("AUTH").value("123435").domain("example.com").path("/").build())
    root.children["zhao"]!!.cookies.add(Cookie.Builder().name("MOD").value("235666").domain("example.com").path("/").build())

    // 将 TrieNode 转换为 XML 字符串
    val xmlString = root.toXmlString()

    // 输出 XML 字符串
    println(xmlString)
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