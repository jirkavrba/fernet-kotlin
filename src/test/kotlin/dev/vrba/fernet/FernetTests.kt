package dev.vrba.fernet

import dev.vrba.fertnet.Fernet
import org.junit.jupiter.api.Test
import java.util.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame

class FernetTests {
    @Test
    fun `keys can be constructed using generateKey on random`() {
        val first = Fernet.generateKey()
        val second = Fernet.generateKey()

        assertNotEquals(first, second)
    }

    @Test
    fun `keys can be constructed using generatedKey with provided random`() {
        val first = Fernet.generateKey(Random(0))
        val second = Fernet.generateKey(Random(0))

        assertNotSame(first, second)
        assertEquals(first, second)
    }
}
