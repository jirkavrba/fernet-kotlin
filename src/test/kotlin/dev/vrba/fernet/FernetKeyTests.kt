package dev.vrba.fernet

import dev.vrba.fertnet.FernetKey
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame

class FernetKeyTests {
    @Test
    fun `keys can be constructed using signing encryption parts`() {
        val signingKey = ByteArray(FernetKey.SIGNING_KEY_LENGTH_BYTES)
        val encryptionKey = ByteArray(FernetKey.ENCRYPTION_KEY_LENGTH_BYTES)

        assertDoesNotThrow {
            val first = FernetKey(signingKey, encryptionKey)
            val second = FernetKey(signingKey, encryptionKey)

            assertNotSame(first, second)
            assertEquals(first, second)
        }
    }

    @Test
    fun `keys should validate signing key size when constructed`() {
        val encryptionKey = ByteArray(FernetKey.ENCRYPTION_KEY_LENGTH_BYTES)

        assertThrows<IllegalArgumentException> {
            val tooLong = ByteArray(FernetKey.SIGNING_KEY_LENGTH_BYTES + 1)

            FernetKey(
                signingKey = tooLong,
                encryptionKey = encryptionKey,
            )
        }

        assertThrows<IllegalArgumentException> {
            val tooShort = ByteArray(FernetKey.SIGNING_KEY_LENGTH_BYTES - 1)

            FernetKey(
                signingKey = tooShort,
                encryptionKey = encryptionKey,
            )
        }
    }

    @Test
    fun `keys should validate encryption key size when constructed`() {
        val signingKey = ByteArray(FernetKey.SIGNING_KEY_LENGTH_BYTES)

        assertThrows<IllegalArgumentException> {
            val tooLong = ByteArray(FernetKey.ENCRYPTION_KEY_LENGTH_BYTES + 1)

            FernetKey(
                signingKey = signingKey,
                encryptionKey = tooLong,
            )
        }

        assertThrows<IllegalArgumentException> {
            val tooShort = ByteArray(FernetKey.ENCRYPTION_KEY_LENGTH_BYTES - 1)

            FernetKey(
                signingKey = signingKey,
                encryptionKey = tooShort,
            )
        }
    }

    @Test
    fun `keys should be generated randomly by default`() {
        val first = FernetKey.generate()
        val second = FernetKey.generate()

        assertNotEquals(first, second)
    }

    @Test
    fun `keys can be generated with a provided random`() {
        val first = FernetKey.generate(Random(0))
        val second = FernetKey.generate(Random(0))

        assertNotSame(first, second)
        assertEquals(first, second)
    }

    @Test
    fun `keys can be encoded to and decoded from base64`() {
        val key = FernetKey.generate()
        val encoded = key.encode()
        val decoded = FernetKey.decode(encoded)

        assertNotSame(key, decoded)
        assertEquals(key, decoded)
    }
}
