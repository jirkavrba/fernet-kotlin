package dev.vrba.fertnet

object Fernet {
    fun generateKey(): Key = Key.generate()
}
