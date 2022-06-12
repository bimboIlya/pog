package com.bimboilya.authist.util

fun <T> Result<T>.mapError(mapper: (Throwable) -> Throwable): Result<T> {
    return when (val error = exceptionOrNull()) {
        null -> this
        else -> Result.failure(mapper(error))
    }
}
