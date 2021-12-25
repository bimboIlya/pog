package com.bimboilya.navsample.jetpack.flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon
import com.bimboilya.navsample.common.navigation.Command.Open
import com.bimboilya.navsample.common.navigation.Command.Pop
import com.bimboilya.navsample.common.navigation.Command.PopUpTo
import com.bimboilya.navsample.common.navigation.CommandDispatcher
import com.bimboilya.navsample.common.navigation.StringResult
import com.bimboilya.navsample.jetpack.ComposableDestination

object FourthScreen : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        GenericScreen(
            title = "Fourth Screen",
            genericAction = { CommandDispatcher.dispatch(Open(FifthScreen)) },
            specialAction = { CommandDispatcher.dispatch(PopUpTo(SecondScreen)) },
            navIcon = NavIcon.Back,
            navAction = { CommandDispatcher.dispatch(Pop) },
            topContent = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { StringResult.set("Fourth screen result") }) {
                        Text("Result")
                    }
                }
            }
        )
    }
}
