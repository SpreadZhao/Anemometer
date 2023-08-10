package util

import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptUtil {
    fun aesEncrypt(toEnc: String, key: String): String {
        val blockSize = 16
        val k = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")
        val crypt = Cipher.getInstance("AES/CBC/NoPadding")
        crypt.init(Cipher.ENCRYPT_MODE, k)

        // Start padding
        val dataToPad = ("xidianscriptsxduxidianscriptsxduxidianscriptsxduxidianscriptsxdu$toEnc").toByteArray(
            StandardCharsets.UTF_8)
        val paddingLength = blockSize - dataToPad.size % blockSize
        val paddedData = ByteArray(dataToPad.size + paddingLength)
        System.arraycopy(dataToPad, 0, paddedData, 0, dataToPad.size)
        for (i in dataToPad.size until paddedData.size) {
            paddedData[i] = paddingLength.toByte()
        }

        // Start encrypt
        val encryptedBytes = crypt.doFinal(paddedData)
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }
}