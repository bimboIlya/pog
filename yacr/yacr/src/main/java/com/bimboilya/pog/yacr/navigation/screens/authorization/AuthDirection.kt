package com.bimboilya.pog.yacr.navigation.screens.authorization

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.Route
import com.bimboilya.pog.yacr.navigation.NavDirection
import com.bimboilya.yacr.feature.authorization.ui.AuthorizationScreen

object AuthDirection : NavDirection {

    override val route: Route = Route("${this::class}")

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        AuthorizationScreen()
    }
}