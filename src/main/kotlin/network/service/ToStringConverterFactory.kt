package network.service

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * https://www.itcodar.com/java/how-to-get-string-response-from-retrofit2.html
 */
class ToStringConverterFactory : Converter.Factory() {

    private val MEDIA_TYPE = MediaType.parse("text/plain")

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (String.Companion::class.java == type) {
            return Converter<ResponseBody, String> { value ->
                value.string()
            }
        }
        return null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return Converter<String, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
    }
}