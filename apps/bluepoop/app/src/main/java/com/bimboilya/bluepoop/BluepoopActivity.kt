package com.bimboilya.bluepoop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.bimboilya.bluepoop.feature.scan.ScanScreen
import com.bimboilya.common.ui.theme.PogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BluepoopActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                Surface {

                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BluepoopApplication() {
    Navigator(ScanScreen()) { navigator ->
        FadeTransition(navigator)
    }
}
