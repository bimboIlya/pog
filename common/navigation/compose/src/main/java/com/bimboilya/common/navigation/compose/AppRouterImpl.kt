package com.bimboilya.common.navigation.compose

import android.content.ActivityNotFoundException
import com.bimboilya.common.navigation.core.ActivityDestination
import com.bimboilya.common.navigation.core.AppRouter
import com.bimboilya.common.navigation.core.Destination
import timber.log.Timber
import javax.inject.Inject

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
            navigate(destination.route)
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

            navigate(destination.route) {
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
            navigate(destination.route) {
                popUpTo(destinationToPopUpTo.route)
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
            popBackStack(destination.route, inclusive)
        }
    }

    override fun popToRoot(inclusive: Boolean) {
        navCommandDispatcher.dispatchComposableNavCommand {
            val rootRoute = graph.startDestinationRoute
            rootRoute?.let { popBackStack(rootRoute, inclusive) }
        }
    }
}