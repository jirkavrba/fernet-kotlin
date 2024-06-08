package dev.vrba.fertnet

import java.security.SecureRandom
import java.util.Random

class Key(
    signingKey: ByteArray,
    encryptionKey: ByteArray,
) {
    private val signingKey: ByteArray = signingKey.copyOf()
    private val encryptionKey: ByteArray = encryptionKey.copyOf()

    init {
        validateKeyLength(signingKey, SIGNING_KEY_LENGTH_BYTES, "Signing key")
        validateKeyLength(encryptionKey, ENCRYPTION_KEY_LENGTH_BYTES, "Encryption key")
    }

    companion object {
        private const val SIGNING_KEY_LENGTH_BYTES = 16
        private const val ENCRYPTION_KEY_LENGTH_BYTES = 16

        fun generate(random: Random = SecureRandom()): Key {
            val signingKey = ByteArray(SIGNING_KEY_LENGTH_BYTES)
            val encryptionKey = ByteArray(ENCRYPTION_KEY_LENGTH_BYTES)

            random.nextBytes(signingKey)
            random.nextBytes(encryptionKey)

            return Key(signingKey, encryptionKey)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun toString(): String {
        return "Key(signingKey=${signingKey.toHexString()}, encryptionKey=${encryptionKey.toHexString()})"
    }

    private fun validateKeyLength(
        key: ByteArray,
        size: Int,
        name: String,
    ) {
        if (key.size != size) {
            throw IllegalArgumentException("$name length must be $size bytes.")
        }
    }
}
