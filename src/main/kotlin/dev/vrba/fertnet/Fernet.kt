package dev.vrba.fertnet

object Fernet {
    /**
     * Generates a new Fernet key that can be used to encrypt and decrypt messages.
     */
    fun generateKey(): FernetKey = FernetKey.generate()
}
