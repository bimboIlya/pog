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
import com.bimboilya.navsample.common.StringResult
import com.bimboilya.navsample.jetpack.ComposableDestination
import com.bimboilya.navsample.jetpack.JetpackCommand.Open
import com.bimboilya.navsample.jetpack.JetpackCommand.Pop
import com.bimboilya.navsample.jetpack.JetpackCommand.PopUpTo
import com.bimboilya.navsample.jetpack.JetpackCommandDispatcher

object FourthScreen : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        GenericScreen(
            title = "Fourth Screen",
            genericAction = { JetpackCommandDispatcher.dispatch(Open(FifthScreen)) },
            specialAction = { JetpackCommandDispatcher.dispatch(PopUpTo(SecondScreen)) },
            navIcon = NavIcon.Back,
            navAction = { JetpackCommandDispatcher.dispatch(Pop) },
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
