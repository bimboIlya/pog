package com.bimboilya.navsample.voyager.flow

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.voyager.CloseFlow
import com.bimboilya.navsample.voyager.MyFlow
import com.bimboilya.navsample.voyager.Pop
import com.bimboilya.navsample.voyager.PopToRoot
import com.bimboilya.navsample.voyager.ScreenDestination
import com.bimboilya.navsample.voyager.VoyagerCommandDispatcher

object FifthDestination : ScreenDestination {

    override fun createScreen(): Screen =
        FifthScreen()
}

class FifthScreen : Screen {

    @Composable
    override fun Content() {
        GenericScreen(
            title = "Fifth Screen",
            navIcon = Back,
            navAction = { VoyagerCommandDispatcher.dispatch(Pop) },
            genericAction = { VoyagerCommandDispatcher.dispatch(PopToRoot) },
            specialButtonText = "CloseFlow",
            specialAction = { VoyagerCommandDispatcher.dispatch(CloseFlow(MyFlow)) }
        )
    }
}
