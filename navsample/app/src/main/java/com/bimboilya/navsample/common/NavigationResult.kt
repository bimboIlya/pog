package com.bimboilya.navsample.common

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.bimboilya.common.ktx.android.observe
import com.bimboilya.navsample.jetpack.flow.Song
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class NavigationResult<T> {

    private val channel = Channel<T>(Channel.CONFLATED)

    fun set(result: T) {
        channel.trySend(result)
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun observe(
        lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
        onResult: suspend (T) -> Unit
    ) {
        val currentOnResult by rememberUpdatedState(onResult)

        LaunchedEffect(lifecycle) {
            channel.receiveAsFlow().observe(lifecycle, currentOnResult)
        }
    }
}

object StringResult : NavigationResult<String>()

object SongResult : NavigationResult<Song>()
