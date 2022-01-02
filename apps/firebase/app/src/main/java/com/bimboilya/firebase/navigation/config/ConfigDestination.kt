package com.bimboilya.firebase.navigation.config

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.jetpack.ComposableDestination
import com.bimboilya.firebase.feature.config.ui.ConfigScreen

object ConfigDestination : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        ConfigScreen(viewModel = hiltViewModel())
    }
}