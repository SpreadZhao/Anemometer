package util

import com.google.gson.Gson

object GsonUtil {
    private val gson = Gson()
    fun toJsonString(obj: Any) = gson.toJson(obj)
}