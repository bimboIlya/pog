package com.bimboilya.common.navigation.jetpack

import android.content.ActivityNotFoundException
import timber.log.Timber
import javax.inject.Inject

interface AppRouter {

    fun open(destination: Destination)

    fun replace(destination: Destination)

    /**
     * start: [A] - [B] - [C] - [D]
     * replace(E, B)
     * end: [A] - [B] - [E]
     */
    fun replace(destination: Destination, destinationToPopUpTo: Destination)

    fun pop()

    fun popUpTo(destination: Destination, inclusive: Boolean = false)

    fun popToRoot(inclusive: Boolean = false)
}

class AppRouterImpl @Inject constructor(
    private val navCommandDispatcher: NavCommandDispatcher,
) : AppRouter {

    override fun open(destination: Destination) {
        when (destination) {
            is ComposableDestination -> navigateComposable(destination)
            is ActivityDestination -> navigateActivity(destination)
        }
    }

    private fun navigateComposable(destination: ComposableDestination) {
        navCommandDispatcher.dispatchComposableNavCommand {
            navigate(destination.getRoute())
        }
    }

    private fun navigateActivity(destination: ActivityDestination) {
        navCommandDispatcher.dispatchNavCommand {
            val intent = destination.createIntent()

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Timber.e(e, "Cannot open activity with intent $intent")
            }
        }
    }

    override fun replace(destination: Destination) {
        if (destination !is ComposableDestination) return

        navCommandDispatcher.dispatchComposableNavCommand {
            val currentRoute = currentDestination?.route

            navigate(destination.getRoute()) {
                currentRoute?.let {
                    popUpTo(currentRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }

    override fun replace(destination: Destination, destinationToPopUpTo: Destination) {
        if (destination !is ComposableDestination || destinationToPopUpTo !is ComposableDestination) return

        navCommandDispatcher.dispatchComposableNavCommand {
            navigate(destination.getRoute()) {
                popUpTo(destinationToPopUpTo.getRoute())
            }
        }
    }

    override fun pop() {
        navCommandDispatcher.dispatchComposableNavCommand {
            popBackStack()
        }
    }

    override fun popUpTo(destination: Destination, inclusive: Boolean) {
        if (destination !is ComposableDestination) return

        navCommandDispatcher.dispatchComposableNavCommand {
            popBackStack(destination.getRoute(), inclusive)
        }
    }

    override fun popToRoot(inclusive: Boolean) {
        navCommandDispatcher.dispatchComposableNavCommand {
            val rootRoute = graph.startDestinationRoute
            rootRoute?.let { popBackStack(rootRoute, inclusive) }
        }
    }
}