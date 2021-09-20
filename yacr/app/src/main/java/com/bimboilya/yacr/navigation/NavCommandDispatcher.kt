package com.bimboilya.yacr.navigation

import android.app.Activity
import androidx.navigation.NavHostController
import com.bimboilya.common.ktx.jvm.SingleMutableEvent
import com.bimboilya.common.ktx.jvm.invoke
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

typealias NavCommand = Activity.() -> Unit
typealias ComposableNavCommand = NavHostController.() -> Unit

interface NavCommandDispatcher {

    val navCommandFlow: SharedFlow<NavCommand>

    val composableNavCommandFlow: SharedFlow<ComposableNavCommand>

    fun dispatchNavCommand(command: NavCommand)

    fun dispatchComposableNavCommand(command: ComposableNavCommand)
}

class NavCommandDispatcherImpl @Inject constructor() : NavCommandDispatcher {

    override val navCommandFlow = SingleMutableEvent<NavCommand>()

    override val composableNavCommandFlow = SingleMutableEvent<ComposableNavCommand>()

    override fun dispatchNavCommand(command: NavCommand) {
        navCommandFlow(command)
    }

    override fun dispatchComposableNavCommand(command: ComposableNavCommand) {
        composableNavCommandFlow(command)
    }
}
