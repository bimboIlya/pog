package com.bimboilya.yacr.navigation.screens.authorization

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.compose.NavDirection
import com.bimboilya.yacr.feature.authorization.ui.AuthorizationScreen

object AuthDirection : NavDirection {

    override val route: String = "${this::class}"

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        AuthorizationScreen(viewModel = hiltViewModel())
    }
}