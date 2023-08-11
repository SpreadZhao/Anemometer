package util

import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptUtil {
    fun aesEncrypt(toEnc: String, key: String): String {
        val blockSize = 16
        val dataToPad =
            ("xidianscriptsxduxidianscriptsxduxidianscriptsxduxidianscriptsxdu$toEnc").toByteArray(StandardCharsets.UTF_8)
        val paddingLength = blockSize - dataToPad.size % blockSize
        val paddedData = dataToPad + ByteArray(paddingLength) { paddingLength.toByte() }
        val readyToEnc = String(paddedData, StandardCharsets.UTF_8)

        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val keySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
        val ivSpec = IvParameterSpec("xidianscriptsxdu".toByteArray(StandardCharsets.UTF_8))
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(readyToEnc.toByteArray(StandardCharsets.UTF_8))

        return Base64.getEncoder().encodeToString(encryptedBytes)
    }
}