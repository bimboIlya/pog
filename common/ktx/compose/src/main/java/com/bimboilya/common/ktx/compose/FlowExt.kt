package com.bimboilya.common.ktx.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bimboilya.common.ktx.android.observe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectInComposition(
  lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
  block: @DisallowComposableCalls suspend (T) -> Unit
) {
  val currentBlock by rememberUpdatedState(block)
  LaunchedEffect(this, lifecycle) {
    observe(lifecycle, currentBlock)
  }
}

@SuppressLint("ComposableNaming")
@Composable
fun Flow<Unit>.collectInComposition(
  lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
  block: @DisallowComposableCalls suspend () -> Unit
) {
  val currentBlock by rememberUpdatedState(block)
  LaunchedEffect(this, lifecycle) {
    observe(lifecycle, block)
  }
}

@Deprecated("", replaceWith = ReplaceWith("collectAsStateWithLifecycle()", imports = ["androidx.lifecycle.compose.collectAsStateWithLifecycle"]))
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
  lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
  context: CoroutineContext = EmptyCoroutineContext
): State<T> =
  remember(this, lifecycle) {
    flowWithLifecycle(lifecycle, minActiveState)
  }.collectAsState(value, context)

@Deprecated("", replaceWith = ReplaceWith("collectAsStateWithLifecycle()", imports = ["androidx.lifecycle.compose.collectAsStateWithLifecycle"]))
@Composable
fun <T> Flow<T>.collectAsStateWithLifecycle(
  initial: T,
  lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
  minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
  context: CoroutineContext = EmptyCoroutineContext
): State<T> =
  remember(this, lifecycle) {
    flowWithLifecycle(lifecycle, minActiveState)
  }.collectAsState(initial, context)
