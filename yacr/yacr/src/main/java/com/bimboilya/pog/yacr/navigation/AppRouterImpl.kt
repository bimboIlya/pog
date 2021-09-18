package com.bimboilya.pog.yacr.navigation

import android.content.ActivityNotFoundException
import com.bimboilya.common.navigation.ActivityDestination
import com.bimboilya.common.navigation.AppRouter
import com.bimboilya.common.navigation.Destination
import com.bimboilya.common.navigation.Route
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

    override fun open(destinationRoute: Route) {
        navCommandDispatcher.dispatchComposableNavCommand {
            navigate(destinationRoute.value)
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

    override fun replace(destinationRoute: Route) {
        navCommandDispatcher.dispatchComposableNavCommand {
            val currentRoute = currentDestination?.route

            navigate(destinationRoute.value) {
                currentRoute?.let {
                    popUpTo(currentRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }

    override fun replace(destinationRoute: Route, routeToPopUpTo: String) {
        navCommandDispatcher.dispatchComposableNavCommand {
            navigate(destinationRoute.value) {
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