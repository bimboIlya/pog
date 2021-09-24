package com.bimboilya.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bimboilya.common.navigation.compose.NavCommandDispatcher
import com.bimboilya.common.navigation.compose.extenstions.composable
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.firebase.navigation.chooser.ChooserDirection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navCommandDispatcher: NavCommandDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            navCommandDispatcher.navCommandFlow.collect { command ->
                command(this@MainActivity) }
        }

        setContent {
            PogTheme {
                Navigation(navCommandDispatcher)
            }
        }
    }
}


@Composable
private fun Navigation(navCommandDispatcher: NavCommandDispatcher) {
    val navController = rememberNavController()

    LaunchedEffect(navCommandDispatcher) {
        navCommandDispatcher.composableNavCommandFlow.collect { command ->
            command(navController)
        }
    }

    NavHost(
        navController,
        startDestination = ChooserDirection.route,
    ) {
        composable(ChooserDirection)
    }
}

