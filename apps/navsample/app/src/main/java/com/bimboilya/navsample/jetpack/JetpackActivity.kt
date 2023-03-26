package com.bimboilya.navsample.jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bimboilya.common.ktx.compose.collectInComposition
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.navsample.jetpack.flow.FifthScreen
import com.bimboilya.navsample.jetpack.flow.FirstScreen
import com.bimboilya.navsample.jetpack.flow.FourthScreen
import com.bimboilya.navsample.jetpack.flow.SecondScreen
import com.bimboilya.navsample.jetpack.flow.ThirdScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class JetpackActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
private fun AppNavigation() {
    val navController = rememberNavController().apply { LogNavigation() }
    val context = LocalContext.current
    val navigator = remember(navController, context) { JetpackNavigator(navController, context) }

    JetpackCommandDispatcher.commandFlow
        .collectInComposition(block = navigator::execute)

    NavHost(
        navController,
        startDestination = FirstScreen.getRoute(),
    ) {
        composable(FirstScreen)
        composable(SecondScreen)
        composable(ThirdScreen)
        composable(FourthScreen)
        composable(FifthScreen)
    }
}

@Composable
fun NavHostController.LogNavigation() {
    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
            controller.currentBackStack.value.joinToString(separator = " → ") { navBackStackEntry ->
                navBackStackEntry.destination.route.orEmpty()
            }.apply(Timber::d)
        }
        addOnDestinationChangedListener(listener)
        onDispose { removeOnDestinationChangedListener(listener) }
    }
}
