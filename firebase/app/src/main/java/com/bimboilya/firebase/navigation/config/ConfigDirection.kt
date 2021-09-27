package com.bimboilya.firebase.navigation.config

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.compose.NavDirection
import com.bimboilya.firebase.feature.config.ui.ConfigScreen

object ConfigDirection : NavDirection {
    override val route = "${this::class}"

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        ConfigScreen(viewModel = hiltViewModel())
    }
}