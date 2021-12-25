package com.bimboilya.navsample.jetpack.flow

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon
import com.bimboilya.navsample.common.navigation.Command.OpenRoute
import com.bimboilya.navsample.common.navigation.Command.Pop
import com.bimboilya.navsample.common.navigation.Command.ReplaceRoute
import com.bimboilya.navsample.common.navigation.CommandDispatcher
import com.bimboilya.navsample.jetpack.ComposableDestination

object FirstScreen : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        GenericScreen(
            title = "First Screen",
            genericAction = { CommandDispatcher.dispatch(OpenRoute(SecondScreen.createRoute())) },
            specialAction = { CommandDispatcher.dispatch(ReplaceRoute(SecondScreen.createRoute("special"))) },
            navIcon = NavIcon.Close,
            navAction = { CommandDispatcher.dispatch(Pop) },
        )
    }
}
