package com.bimboilya.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.bimboilya.common.ktx.android.collectInComposition
import com.bimboilya.common.navigation.voyager.CommandDispatcher
import com.bimboilya.common.navigation.voyager.NavigationController
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.firebase.feature.chooser.ChooserDestination
import com.bimboilya.firebase.util.NavigationLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseActivity : ComponentActivity() {

    @Inject
    lateinit var commandDispatcher: CommandDispatcher

    @Inject
    lateinit var navigationLogger: NavigationLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                Surface {
                    FirebaseApp(commandDispatcher, navigationLogger)
                }
            }
        }
    }
}

@Composable
private fun FirebaseApp(
    commandDispatcher: CommandDispatcher,
    navigationLogger: NavigationLogger
) {
    Navigator(ChooserDestination().createScreen()) { navigator ->
        val context = LocalContext.current
        val navController = remember(navigator, context) {
            NavigationController(navigator, context)
        }

        commandDispatcher.commandFlow.collectInComposition { command ->
            navController.executeCommand(command)
            navigator.items
                .map { screen -> screen.key.substringAfterLast('.') }
                .let(navigationLogger::setCurrentNavBackStack)
        }

        CurrentScreen()
    }
}
