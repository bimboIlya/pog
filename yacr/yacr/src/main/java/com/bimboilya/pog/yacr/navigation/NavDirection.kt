package com.bimboilya.pog.yacr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NamedNavArgument
import com.bimboilya.common.navigation.Route

interface NavDirection {

    val route: Route

    fun getArguments(): List<NamedNavArgument> = emptyList()

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)
}
