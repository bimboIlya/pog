@file:Suppress("UNCHECKED_CAST")

package com.bimboilya.common.ktx.jvm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

fun <T1, T2, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    transform: (T1, T2) -> R,
): StateFlow<R> =
    combine(flow1, flow2) { arg1, arg2 ->
        transform(arg1, arg2)
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(flow1.value, flow2.value)
    )

fun <T1, T2, T3, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    transform: (T1, T2, T3) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3) { arg1, arg2, arg3 ->
        transform(arg1, arg2, arg3)
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(flow1.value, flow2.value, flow3.value)
    )

fun <T1, T2, T3, T4, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    transform: (T1, T2, T3, T4) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4) { arg1, arg2, arg3, arg4 ->
        transform(arg1, arg2, arg3, arg4)
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(flow1.value, flow2.value, flow3.value, flow4.value)
    )

fun <T1, T2, T3, T4, T5, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    transform: (T1, T2, T3, T4, T5) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5) { arg1, arg2, arg3, arg4, arg5 ->
        transform(arg1, arg2, arg3, arg4, arg5)
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value)
    )

fun <T1, T2, T3, T4, T5, T6, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    flow6: StateFlow<T6>,
    transform: (T1, T2, T3, T4, T5, T6) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
        transform(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4, args[4] as T5, args[5] as T6)
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value, flow6.value)
    )

fun <T1, T2, T3, T4, T5, T6, T7, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    flow6: StateFlow<T6>,
    flow7: StateFlow<T7>,
    transform: (T1, T2, T3, T4, T5, T6, T7) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5, flow6, flow7) { args: Array<*> ->
        transform(args[0] as T1, args[1] as T2, args[2] as T3, args[3] as T4, args[4] as T5, args[5] as T6, args[6] as T7)
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value, flow6.value, flow7.value)
    )

fun <T1, T2, T3, T4, T5, T6, T7, T8, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    flow6: StateFlow<T6>,
    flow7: StateFlow<T7>,
    flow8: StateFlow<T8>,
    transform: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5, flow6, flow7, flow8) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
        )
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(
            flow1.value,
            flow2.value,
            flow3.value,
            flow4.value,
            flow5.value,
            flow6.value,
            flow7.value,
            flow8.value,
        )
    )

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    flow6: StateFlow<T6>,
    flow7: StateFlow<T7>,
    flow8: StateFlow<T8>,
    flow9: StateFlow<T9>,
    transform: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
            args[8] as T9,
        )
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(
            flow1.value,
            flow2.value,
            flow3.value,
            flow4.value,
            flow5.value,
            flow6.value,
            flow7.value,
            flow8.value,
            flow9.value,
        )
    )

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> CoroutineScope.combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    flow6: StateFlow<T6>,
    flow7: StateFlow<T7>,
    flow8: StateFlow<T8>,
    flow9: StateFlow<T9>,
    flow10: StateFlow<T10>,
    transform: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): StateFlow<R> =
    combine(flow1, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9, flow10) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
            args[7] as T8,
            args[8] as T9,
            args[9] as T10,
        )
    }.stateIn(this,
        SharingStarted.WhileSubscribed(),
        transform(
            flow1.value,
            flow2.value,
            flow3.value,
            flow4.value,
            flow5.value,
            flow6.value,
            flow7.value,
            flow8.value,
            flow9.value,
            flow10.value
        )
    )