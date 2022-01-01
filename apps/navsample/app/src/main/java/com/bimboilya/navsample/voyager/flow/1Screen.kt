package com.bimboilya.navsample.voyager.flow

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon
import com.bimboilya.navsample.voyager.Open
import com.bimboilya.navsample.voyager.Pop
import com.bimboilya.navsample.voyager.Replace
import com.bimboilya.navsample.voyager.ScreenDestination
import com.bimboilya.navsample.voyager.VoyagerCommandDispatcher

object FirstDestination : ScreenDestination {

    override fun createScreen(): Screen =
        FirstScreen()
}

class FirstScreen : Screen {

    @Composable
    override fun Content() {
        GenericScreen(
            title = "First Screen",
            genericAction = { VoyagerCommandDispatcher.dispatch(Open(SecondDestination)) },
            specialButtonText = "Replace",
            specialAction = { VoyagerCommandDispatcher.dispatch(Replace(SecondDestination)) },
            navIcon = NavIcon.Close,
            navAction = { VoyagerCommandDispatcher.dispatch(Pop) },
        )
    }
}
