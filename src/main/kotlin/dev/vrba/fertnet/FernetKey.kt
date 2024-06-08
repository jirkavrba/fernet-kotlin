package dev.vrba.fertnet

import java.security.SecureRandom
import java.util.Base64
import java.util.Objects
import java.util.Random

@OptIn(ExperimentalStdlibApi::class)
class FernetKey(
    signingKey: ByteArray,
    encryptionKey: ByteArray,
) {
    private val signingKey: ByteArray = signingKey.copyOf()
    private val encryptionKey: ByteArray = encryptionKey.copyOf()
    private val encodedBase64: ByteArray by lazy {
        Base64.getEncoder().encode(signingKey.copyOf() + encryptionKey.copyOf())
    }

    init {
        validateKeyLength(signingKey, SIGNING_KEY_LENGTH_BYTES, "Signing key")
        validateKeyLength(encryptionKey, ENCRYPTION_KEY_LENGTH_BYTES, "Encryption key")
    }

    companion object {
        const val SIGNING_KEY_LENGTH_BYTES = 16
        const val ENCRYPTION_KEY_LENGTH_BYTES = 16

        fun generate(random: Random = SecureRandom()): FernetKey {
            val signingKey = ByteArray(SIGNING_KEY_LENGTH_BYTES)
            val encryptionKey = ByteArray(ENCRYPTION_KEY_LENGTH_BYTES)

            random.nextBytes(signingKey)
            random.nextBytes(encryptionKey)

            return FernetKey(signingKey, encryptionKey)
        }

        fun decode(encodedKey: ByteArray): FernetKey {
            val decoded = Base64.getDecoder().decode(encodedKey)
            val signingKey = decoded.copyOfRange(0, SIGNING_KEY_LENGTH_BYTES)
            val encryptionKey = decoded.copyOfRange(SIGNING_KEY_LENGTH_BYTES, decoded.size)

            return FernetKey(signingKey, encryptionKey)
        }
    }

    fun encode(): ByteArray = encodedBase64

    override fun hashCode(): Int {
        return Objects.hash(
            signingKey,
            encryptionKey,
        )
    }

    override fun equals(other: Any?): Boolean {
        return other is FernetKey &&
            other.signingKey.contentEquals(signingKey) &&
            other.encryptionKey.contentEquals(encryptionKey)
    }

    override fun toString(): String {
        return "Key(${encode().toHexString()})"
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
