package com.bimboilya.navsample.voyager.flow

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon
import com.bimboilya.navsample.voyager.Open
import com.bimboilya.navsample.voyager.Pop
import com.bimboilya.navsample.voyager.PopTo
import com.bimboilya.navsample.voyager.ScreenDestination
import com.bimboilya.navsample.voyager.VoyagerCommandDispatcher

object FourthDestination : ScreenDestination {

    override fun createScreen(): Screen =
        FourthScreen()
}

class FourthScreen : Screen {

    @Composable
    override fun Content() {
        GenericScreen(
            title = "Fourth Screen",
            genericAction = { VoyagerCommandDispatcher.dispatch(Open(FifthDestination)) },
            specialButtonText = "To Second",
            specialAction = { VoyagerCommandDispatcher.dispatch(PopTo(SecondScreen::class)) },
            navIcon = NavIcon.Back,
            navAction = { VoyagerCommandDispatcher.dispatch(Pop) },
        )
    }
}
