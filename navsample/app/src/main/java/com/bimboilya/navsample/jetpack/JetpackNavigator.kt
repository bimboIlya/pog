package com.bimboilya.navsample.jetpack

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bimboilya.navsample.common.navigation.ActivityDestination
import com.bimboilya.navsample.common.navigation.Command
import com.bimboilya.navsample.common.navigation.Command.CloseFlow
import com.bimboilya.navsample.common.navigation.Command.Composite
import com.bimboilya.navsample.common.navigation.Command.Open
import com.bimboilya.navsample.common.navigation.Command.OpenRoute
import com.bimboilya.navsample.common.navigation.Command.Pop
import com.bimboilya.navsample.common.navigation.Command.PopUpTo
import com.bimboilya.navsample.common.navigation.Command.PopUpToRoot
import com.bimboilya.navsample.common.navigation.Command.Replace
import com.bimboilya.navsample.common.navigation.Command.ReplaceRoute
import com.bimboilya.navsample.common.navigation.Destination
import com.bimboilya.navsample.common.navigation.Navigator
import timber.log.Timber

class JetpackNavigator(
    private val navController: NavHostController,
    context: Context,
) : Navigator {

    private val activity: ComponentActivity = checkNotNull(context as? ComponentActivity) {
        "LocalContext is not an Activity but $context"
    }

    override fun execute(command: Command) {
        when (command) {
            is Open -> open(command.destination)
            is OpenRoute -> openRoute(command.route)
            is Replace -> replace(command.destination)
            is ReplaceRoute -> replaceRoute(command.route)
            Pop -> pop()
            is PopUpTo -> popUpTo(command.destination)
            PopUpToRoot -> popUpToRoot()
            CloseFlow -> closeFlow()
            is Composite -> composite(command.commands)
        }
    }

    private fun open(destination: Destination) {
        when (destination) {
            is ActivityDestination -> openActivity(destination)
            is ComposableDestination -> openComposable(destination)
            is ComposableWithArgsDestination -> useRouteError(destination)
            is TabDestination -> openTab(destination)
            is FlowDestination -> openFlow(destination)
        }
    }

    private fun openActivity(destination: ActivityDestination) {
        destination.createIntent(activity)
            .runCatching(activity::startActivity)
            .onFailure(Timber::e)
    }

    private fun openComposable(destination: ComposableDestination) {
        navController.navigate(destination.getRoute())
    }

    private fun openTab(destination: TabDestination) {
        navController.navigate(destination.getRoute()) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun openFlow(destination: FlowDestination) {
        navController.navigate(destination.getRoute())
    }

    // крашится после '/' и отрезает аргумент после '?'
    private fun openRoute(route: String) {
        navController.navigate(route)
    }

    private fun replace(destination: Destination) {
        if (destination is ComposableWithArgsDestination) useRouteError(destination)
        if (destination !is ComposableDestination) return

        val currentRoute = navController.currentDestination?.route

        navController.navigate(destination.getRoute()) {
            currentRoute?.let {
                popUpTo(currentRoute) { inclusive = true }
            }
        }
    }

    private fun replaceRoute(route: String) {
        val currentRoute = navController.currentDestination?.route

        navController.navigate(route) {
            currentRoute?.let {
                popUpTo(currentRoute) { inclusive = true }
            }
        }
    }

    private fun pop() {
        if (navController.backstackCount > 1) {
            navController.popBackStack()
        } else {
            activity.finish()
        }
    }

    private val NavHostController.backstackCount: Int
        get() = backQueue.count { entry ->
            entry.destination !is NavGraph
        }

    private fun popUpTo(destination: Destination) {
        val route = when (destination) {
            is ComposableDestination -> destination.getRoute()
            is ComposableWithArgsDestination -> destination.getRoute()
            else -> return
        }

        navController.popBackStack(route, inclusive = false)
    }

    private fun popUpToRoot() {
        navController.backQueue.firstOrNull { it.destination !is NavGraph }?.destination?.route
            ?.let { navController.popBackStack(it, inclusive = false) }
    }

    private fun closeFlow() {
        navController.backQueue.lastOrNull { it.destination is NavGraph }?.destination?.route
            ?.let { navController.popBackStack(it, inclusive = false) }
    }

    private fun composite(commands: List<Command>) {
        commands.forEach(::execute)
    }

    private fun useRouteError(destination: ComposableWithArgsDestination) {
        throw IllegalArgumentException("Use OpenRoute for ComposableWithArgs. Tried to open: $destination")
    }
}