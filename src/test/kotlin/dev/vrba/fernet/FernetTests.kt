package dev.vrba.fernet

import dev.vrba.fertnet.Fernet
import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals

class FernetTests {
    @Test
    fun `keys can be constructed using generateKey on random`() {
        val first = Fernet.generateKey()
        val second = Fernet.generateKey()

        assertNotEquals(first, second)
    }
}
