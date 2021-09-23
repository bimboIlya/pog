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
            is ActivityDestination -> navigateActivity(destination)
        }
    }

    override fun open(destinationRoute: String) {
        navCommandDispatcher.dispatchComposableNavCommand {
            navigate(destinationRoute)
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

    override fun replace(destinationRoute: String) {
        navCommandDispatcher.dispatchComposableNavCommand {
            val currentRoute = currentDestination?.route

            navigate(destinationRoute) {
                currentRoute?.let {
                    popUpTo(currentRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }

    override fun replace(destinationRoute: String, routeToPopUpTo: String) {
        navCommandDispatcher.dispatchComposableNavCommand {
            navigate(destinationRoute) {
                popUpTo(routeToPopUpTo)
            }
        }
    }

    override fun pop() {
        navCommandDispatcher.dispatchComposableNavCommand {
            popBackStack()
        }
    }

    override fun popUpTo(destinationRoute: String, inclusive: Boolean) {
        navCommandDispatcher.dispatchComposableNavCommand {
            popBackStack(destinationRoute, inclusive)
        }
    }

    override fun popToRoot(inclusive: Boolean) {
        navCommandDispatcher.dispatchComposableNavCommand {
            val rootRoute = graph.startDestinationRoute
            rootRoute?.let { popBackStack(rootRoute, inclusive) }
        }
    }
}