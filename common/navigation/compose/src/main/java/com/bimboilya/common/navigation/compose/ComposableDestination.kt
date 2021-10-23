package com.bimboilya.common.navigation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NamedNavArgument
import com.bimboilya.common.navigation.core.Destination

interface ComposableDestination : Destination {

    fun getRoute(): String = javaClass.simpleName

    fun getArguments(): List<NamedNavArgument> = emptyList()

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)
}
