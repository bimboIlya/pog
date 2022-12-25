package com.bimboilya.navsample.voyager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.bimboilya.common.ktx.compose.collectInComposition
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.navsample.voyager.flow.FirstDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoyagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                NavigationContainer()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun NavigationContainer() {
        Navigator(
            screen = FirstDestination.createScreen(),
            onBackPressed = null,
        ) { navigator ->
            val context = LocalContext.current
            val navController = remember(navigator, context) {
                VoyagerNavigationController(navigator, context)
            }

            VoyagerCommandDispatcher.commandFlow
                .collectInComposition(block = navController::executeCommand)

            FadeTransition(navigator, Modifier.fillMaxSize())
        }
    }
}
