package util

import okhttp3.RequestBody
import java.io.IOException
import java.nio.charset.Charset
import okio.Buffer

object PrintUtil {
    fun getBodyContent(body: RequestBody): String {
        val buffer = Buffer()
        try {
            body.writeTo(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        var charset = Charset.forName("UTF-8");
        val contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"))!!;
        }
        return buffer.readString(charset);
    }
}