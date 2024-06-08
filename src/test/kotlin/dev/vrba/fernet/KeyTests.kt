package dev.vrba.fernet

import dev.vrba.fertnet.Key
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame

class KeyTests {
    @Test
    fun `keys can be constructed using signing encryption parts`() {
        val signingKey = ByteArray(Key.SIGNING_KEY_LENGTH_BYTES)
        val encryptionKey = ByteArray(Key.ENCRYPTION_KEY_LENGTH_BYTES)

        assertDoesNotThrow {
            val first = Key(signingKey, encryptionKey)
            val second = Key(signingKey, encryptionKey)

            assertNotSame(first, second)
            assertEquals(first, second)
        }
    }

    @Test
    fun `keys should validate signing key size when constructed`() {
        val encryptionKey = ByteArray(Key.ENCRYPTION_KEY_LENGTH_BYTES)

        assertThrows<IllegalArgumentException> {
            val tooLong = ByteArray(Key.SIGNING_KEY_LENGTH_BYTES + 1)

            Key(
                signingKey = tooLong,
                encryptionKey = encryptionKey,
            )
        }

        assertThrows<IllegalArgumentException> {
            val tooShort = ByteArray(Key.SIGNING_KEY_LENGTH_BYTES - 1)

            Key(
                signingKey = tooShort,
                encryptionKey = encryptionKey,
            )
        }
    }

    @Test
    fun `keys should validate encryption key size when constructed`() {
        val signingKey = ByteArray(Key.SIGNING_KEY_LENGTH_BYTES)

        assertThrows<IllegalArgumentException> {
            val tooLong = ByteArray(Key.ENCRYPTION_KEY_LENGTH_BYTES + 1)

            Key(
                signingKey = signingKey,
                encryptionKey = tooLong,
            )
        }

        assertThrows<IllegalArgumentException> {
            val tooShort = ByteArray(Key.ENCRYPTION_KEY_LENGTH_BYTES - 1)

            Key(
                signingKey = signingKey,
                encryptionKey = tooShort,
            )
        }
    }

    @Test
    fun `keys should be generated randomly by default`() {
        val first = Key.generate()
        val second = Key.generate()

        assertNotEquals(first, second)
    }

    @Test
    fun `keys can be generated with a provided random`() {
        val first = Key.generate(Random(0))
        val second = Key.generate(Random(0))

        assertNotSame(first, second)
        assertEquals(first, second)
    }

    @Test
    fun `keys can be encoded to and decoded from base64`() {
        val key = Key.generate()
        val encoded = key.encode()
        val decoded = Key.decode(encoded)

        assertNotSame(key, decoded)
        assertEquals(key, decoded)
    }
}
