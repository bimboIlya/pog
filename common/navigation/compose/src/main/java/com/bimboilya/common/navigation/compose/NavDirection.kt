package com.bimboilya.common.navigation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NamedNavArgument

interface NavDirection {

    // todo попробовать рефлексию, чтобы не писать везде ${this::class}
    val route: String

    fun getArguments(): List<NamedNavArgument> = emptyList()

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)
}
