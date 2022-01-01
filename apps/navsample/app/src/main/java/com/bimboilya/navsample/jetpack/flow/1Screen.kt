package com.bimboilya.navsample.jetpack.flow

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon
import com.bimboilya.navsample.jetpack.ComposableDestination
import com.bimboilya.navsample.jetpack.JetpackCommand.OpenRoute
import com.bimboilya.navsample.jetpack.JetpackCommand.Pop
import com.bimboilya.navsample.jetpack.JetpackCommand.ReplaceRoute
import com.bimboilya.navsample.jetpack.JetpackCommandDispatcher

object FirstScreen : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        GenericScreen(
            title = "First Screen",
            genericAction = { JetpackCommandDispatcher.dispatch(OpenRoute(SecondScreen.createRoute())) },
            specialAction = { JetpackCommandDispatcher.dispatch(ReplaceRoute(SecondScreen.createRoute("special"))) },
            navIcon = NavIcon.Close,
            navAction = { JetpackCommandDispatcher.dispatch(Pop) },
        )
    }
}
