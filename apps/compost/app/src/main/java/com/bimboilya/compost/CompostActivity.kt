package com.bimboilya.compost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.FadeTransition
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.compost.swipe.SwipeScreen
import com.bimboilya.compost.ui.FlatToolbar
import com.bimboilya.compost.ui.NavigationBarSpacer
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
class CompostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent)
            }

            PogTheme {
                Surface {
                    Navigator(ChooserScreen()) { navigator ->
                        FadeTransition(navigator)
                    }
                }
            }
        }
    }
}

class ChooserScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                FlatToolbar(
                    title = { Text(text = "Choose your own adventure") },
                )
            },
            bottomBar = { NavigationBarSpacer() }
        ) {
            Column(
                Modifier.padding(it).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { navigator.push(SwipeScreen()) }) {
                    Text(text = "Swipe Screen")
                }
            }
        }
    }
}
