package com.bimboilya.common.navigation.jetpack

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

interface Destination

interface ActivityDestination : Destination {

    fun createIntent(): Intent
}

interface ComposableDestination : Destination {

    fun getRoute(): String = javaClass.simpleName

    fun getArguments(): List<NamedNavArgument> = emptyList()

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)
}
