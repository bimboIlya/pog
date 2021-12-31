package com.bimboilya.navsample.voyager

import android.content.Context
import androidx.activity.ComponentActivity
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import timber.log.Timber
import kotlin.reflect.KClass

class VoyagerNavigationController(
    private val navigator: Navigator,
    context: Context,
) {

    private val activity = checkNotNull(context as? ComponentActivity) {
        "Context is not ComponentActivity but $context"
    }

    fun executeCommand(command: VoyagerCommand) {
        when (command) {
            is Open -> open(command.destination)
            Pop -> pop()
            is PopTo -> popTo(command.screenClass)
            PopToRoot -> popToRoot()
            is Replace -> replace(command.destination)
            is CloseFlow -> closeFlow(command.userFlow)
            is Composite -> composite(command.commands)
        }

        val backStack = navigator.items.joinToString(separator = " → ", prefix = "→") { it.key.substringAfterLast('.') }
        Timber.d(backStack)
    }

    private fun open(destination: VoyagerDestination) {
        when (destination) {
            is ActivityDestination -> openActivity(destination)
            is ScreenDestination -> openScreen(destination)
        }
    }

    private fun openActivity(destination: ActivityDestination) {
        destination.createIntent(activity)
            .runCatching(activity::startActivity)
            .onFailure(Timber::e)
    }

    private fun openScreen(destination: ScreenDestination) {
        val screen = destination.createScreen()
        navigator.push(screen)
    }

    private fun pop() {
        if (navigator.canPop) {
            navigator.pop()
        } else {
            activity.finish()
        }
    }

    private fun popTo(screenClass: KClass<out Screen>) {
        navigator.popUntil { screen ->
            screen::class == screenClass
        }
    }

    private fun popToRoot() {
        navigator.popUntilRoot()
    }

    private fun replace(destination: VoyagerDestination) {
        if (destination !is ScreenDestination) return

        val screen = destination.createScreen()
        navigator.replace(screen)
    }

    private fun closeFlow(userFlow: UserFlow) {
        navigator.popUntil { screen ->
            screen.key.contains(userFlow.name)
        }
        navigator.pop()
    }

    private fun composite(commands: List<VoyagerCommand>) {
        commands.forEach(::executeCommand)
    }
}