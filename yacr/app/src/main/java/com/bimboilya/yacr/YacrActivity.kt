package com.bimboilya.yacr

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
import com.bimboilya.yacr.navigation.screens.authorization.AuthDirection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class YacrActivity : ComponentActivity() {

    @Inject
    lateinit var navCommandDispatcher: NavCommandDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            navCommandDispatcher.navCommandFlow
                .collect { it(this@YacrActivity) }
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