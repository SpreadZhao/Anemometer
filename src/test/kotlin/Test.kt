import org.apache.logging.log4j.LogManager
import java.lang.StringBuilder


fun main() {

    println(parsePathToList("/auth/abc/def"))

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