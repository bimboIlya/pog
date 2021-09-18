package com.bimboilya.common.ktx.jvm

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

inline fun CoroutineScope.launchCatching(
    crossinline onError: (Throwable) -> Unit,
    crossinline block: suspend CoroutineScope.() -> Unit,
): Job =
    this.launch(CoroutineExceptionHandler { _, throwable -> onError(throwable) }) {
        block()
    }


fun <T> SingleMutableEvent() = MutableSharedFlow<T>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

operator fun <T> MutableSharedFlow<T>.invoke(value: T) {
    tryEmit(value)
}