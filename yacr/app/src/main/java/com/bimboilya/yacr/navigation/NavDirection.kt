package com.bimboilya.yacr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NamedNavArgument

interface NavDirection {

    val route: String

    fun getArguments(): List<NamedNavArgument> = emptyList()

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)
}
