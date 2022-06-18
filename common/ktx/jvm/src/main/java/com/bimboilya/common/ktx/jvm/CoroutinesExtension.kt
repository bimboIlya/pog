package com.bimboilya.common.ktx.jvm

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

inline fun CoroutineScope.launchCatching(
    crossinline onError: (Throwable) -> Unit,
    crossinline block: suspend CoroutineScope.() -> Unit,
): Job =
    this.launch(CoroutineExceptionHandler { _, throwable -> onError(throwable) }) {
        block()
    }
