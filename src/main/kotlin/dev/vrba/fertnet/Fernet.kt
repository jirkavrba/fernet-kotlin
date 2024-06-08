package dev.vrba.fertnet

import java.security.SecureRandom
import java.util.Random

object Fernet {
    fun generateKey(random: Random = SecureRandom()): Key = Key.generate(random)
}
