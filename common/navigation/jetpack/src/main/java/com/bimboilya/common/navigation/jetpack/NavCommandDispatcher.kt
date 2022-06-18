package com.bimboilya.common.navigation.jetpack

import android.app.Activity
import androidx.navigation.NavHostController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

typealias NavCommand = Activity.() -> Unit
typealias ComposableNavCommand = NavHostController.() -> Unit

interface NavCommandDispatcher {

    val navCommandFlow: Flow<NavCommand>

    val composableNavCommandFlow: Flow<ComposableNavCommand>

    fun dispatchNavCommand(command: NavCommand)

    fun dispatchComposableNavCommand(command: ComposableNavCommand)
}

class NavCommandDispatcherImpl @Inject constructor() : NavCommandDispatcher {

    private val _navCommandFlow = Channel<NavCommand>(Channel.CONFLATED)
    override val navCommandFlow = _navCommandFlow.receiveAsFlow()

    private val _composableNavCommandFlow = Channel<ComposableNavCommand>(Channel.CONFLATED)
    override val composableNavCommandFlow = _composableNavCommandFlow.receiveAsFlow()

    override fun dispatchNavCommand(command: NavCommand) {
        _navCommandFlow.trySend(command)
    }

    override fun dispatchComposableNavCommand(command: ComposableNavCommand) {
        _composableNavCommandFlow.trySend(command)
    }
}
