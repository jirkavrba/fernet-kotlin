package dev.vrba.fertnet

import java.time.Duration

object Fernet {
    /**
     * Generates a new fernet key that can be used to encrypt and decrypt messages.
     *
     * @see FernetKey
     */
    fun generateKey(): FernetKey = FernetKey.generate()

    /**
     * Encrypts the provided payload with the provided fernet key.
     * The result is a fernet token that can be later decrypted or verified.
     *
     * @see FernetToken
     */
    fun encrypt(
        key: FernetKey,
        payload: ByteArray,
    ): FernetToken = TODO("Not implemented yet.")

    /**
     * Decrypts the provided fernet token with the provided key.
     * If the `maxTokenAge` parameter is provided, a TTL check is performed and a `ExpiredFernetTokenException` is thrown in case of an expired token.
     *
     * @see FernetToken
     */
    fun decrypt(
        key: FernetKey,
        token: FernetToken,
        maxTokenAge: Duration? = null,
    ): ByteArray = TODO("Not implemented yet.")
}
