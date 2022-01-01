package com.bimboilya.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bimboilya.common.ktx.android.hideKeyboard
import com.bimboilya.common.navigation.compose.NavCommandDispatcher
import com.bimboilya.common.navigation.compose.extenstions.composable
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.firebase.navigation.chooser.ChooserDestination
import com.bimboilya.firebase.navigation.config.ConfigDestination
import com.bimboilya.firebase.navigation.crashlytics.CrashDestination
import com.bimboilya.firebase.navigation.crashlytics.CrashSettingsDestination
import com.bimboilya.firebase.util.CrashlyticsNavigationLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseActivity : ComponentActivity() {

    @Inject
    lateinit var navCommandDispatcher: NavCommandDispatcher

    @Inject
    lateinit var crashlyticsNavigationLogger: CrashlyticsNavigationLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            navCommandDispatcher.navCommandFlow.collect { command ->
                command(this@FirebaseActivity)
            }
        }

        setContent {
            PogTheme {
                Navigation(navCommandDispatcher) { navBackStackRoutes ->
                    hideKeyboard(clearFocus = true)
                    crashlyticsNavigationLogger.setCurrentNavBackStackRoutes(navBackStackRoutes)
                }
            }
        }
    }
}

@Composable
private fun Navigation(
    navCommandDispatcher: NavCommandDispatcher,
    onChangeDestination: (List<String>) -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(navCommandDispatcher) {
        navCommandDispatcher.composableNavCommandFlow.collect { command ->
            command(navController)
        }
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
            val navBackStack = controller.backQueue.map { navBackStackEntry ->
                navBackStackEntry.destination.route ?: ""
            }
            onChangeDestination(navBackStack)
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    NavHost(
        navController,
        startDestination = ChooserDestination.getRoute(),
    ) {
        composable(ChooserDestination)
        composable(ConfigDestination)
        composable(CrashDestination)
        composable(CrashSettingsDestination)
    }
}
