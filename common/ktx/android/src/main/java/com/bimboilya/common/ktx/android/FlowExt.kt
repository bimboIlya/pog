package com.bimboilya.common.ktx.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

suspend inline fun <T> Flow<T>.observe(
    lifecycle: Lifecycle,
    crossinline block: suspend (T) -> Unit
) {
    flowWithLifecycle(lifecycle).collect { block(it) }
}

suspend inline fun Flow<Unit>.observe(lifecycle: Lifecycle, crossinline block: suspend () -> Unit) {
    flowWithLifecycle(lifecycle).collect { block() }
}
