package com.bimboilya.yacr.navigation.screens.authorization

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.bimboilya.yacr.feature.authorization.ui.AuthorizationScreen
import com.bimboilya.yacr.navigation.NavDirection

object AuthDirection : NavDirection {

    override val route: String = "${this::class}"

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        AuthorizationScreen()
    }
}