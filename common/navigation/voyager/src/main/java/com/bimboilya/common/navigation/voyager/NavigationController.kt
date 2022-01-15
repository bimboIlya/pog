package com.bimboilya.common.navigation.voyager

import android.app.Activity
import android.content.Context
import cafe.adriel.voyager.navigator.Navigator
import timber.log.Timber
import kotlin.reflect.KClass

class NavigationController(
    private val navigator: Navigator,
    context: Context,
) {

    private val activity = checkNotNull(context as? Activity) {
        "Context is not Activity but $context"
    }

    fun executeCommand(command: Command) {
        when (command) {
            is Open -> open(command.destination)
            Pop -> pop()
            is PopTo -> popTo(command.destinationClass)
            PopToRoot -> popToRoot()
            is Replace -> replace(command.destination)
        }
    }

    private fun open(destination: Destination) {
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

    private fun popTo(destinationClass: KClass<out Destination>) {
        navigator.popUntil { screen ->
            screen.key.contains(requireNotNull(destinationClass.qualifiedName))
        }
    }

    private fun popToRoot() {
        navigator.popUntilRoot()
    }

    private fun replace(destination: Destination) {
        if (destination !is ScreenDestination) return

        val screen = destination.createScreen()
        navigator.replace(screen)
    }
}