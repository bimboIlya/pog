package com.bimboilya.authist.domain.entity

sealed class AuthError(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause) {

    class Cancelled(override val cause: Throwable? = null) : AuthError("Authorization was cancelled", cause)

    class EmptyAccount(override val cause: Throwable? = null) : AuthError("Social network account is empty")

    class Unknown(override val cause: Throwable? = null) : AuthError("Something went wrong ©")
}
