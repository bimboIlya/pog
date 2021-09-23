package com.bimboilya.yacr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.yacr.navigation.NavCommandDispatcher
import com.bimboilya.yacr.navigation.extenstions.composable
import com.bimboilya.yacr.navigation.screens.authorization.AuthDirection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navCommandDispatcher: NavCommandDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            navCommandDispatcher.navCommandFlow
                .collect { it(this@MainActivity) }
        }

        setContent {
            PogTheme {
                Navigation(navCommandDispatcher)
            }
        }
    }
}

@Composable
fun Navigation(navCommandDispatcher: NavCommandDispatcher) {
    val navController = rememberNavController()

    LaunchedEffect(navCommandDispatcher) {
        navCommandDispatcher.composableNavCommandFlow.collect { command ->
            command(navController)
        }
    }

    NavHost(
        navController,
        startDestination = AuthDirection.route,
    ) {
        composable(AuthDirection)
    }
}
