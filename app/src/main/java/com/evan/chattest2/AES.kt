package com.evan.chattest2

import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.spec.SecretKeySpec

class AES {

    companion object{
        private val encryptionKey:ByteArray = byteArrayOf(9, 115, 51, 86, 105, 4, -31, -23, -68, 88, 17, 20, 3, -105, 119, -53)
        private val cipher = Cipher.getInstance("AES")
        private val decipher = Cipher.getInstance("AES")
        private val secretKeySpec = SecretKeySpec(encryptionKey, "AES")
        fun AESEncryptionMethod(string: String): String {
            val stringByte = string.toByteArray()
            var encryptedByte: ByteArray = ByteArray(stringByte.size)
            try {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
                encryptedByte = cipher.doFinal(stringByte)
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }
            var returnString: String = string
            try {
                returnString = String(encryptedByte, Charsets.ISO_8859_1)
            } catch (e: UnsupportedEncodingException) {

                e.printStackTrace()
            }
            return returnString
        }


        @Throws(UnsupportedEncodingException::class)
        fun AESDecryptionMethod(string: String): String {
            val EncryptedByte = string.toByteArray(charset("ISO-8859-1"))
            var decryptedString: String = string
            val decryption: ByteArray
            try {
                decipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
                decryption = decipher.doFinal(EncryptedByte)
                decryptedString = String(decryption)
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            }
            return decryptedString
        }
    }
}