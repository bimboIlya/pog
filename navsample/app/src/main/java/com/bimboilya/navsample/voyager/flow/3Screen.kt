package com.bimboilya.navsample.voyager.flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.common.StringResult
import com.bimboilya.navsample.voyager.MyFlow
import com.bimboilya.navsample.voyager.Open
import com.bimboilya.navsample.voyager.Pop
import com.bimboilya.navsample.voyager.ScreenDestination
import com.bimboilya.navsample.voyager.VoyagerCommandDispatcher

class ThirdDestination(val arg: String) : ScreenDestination {

    override fun createScreen(): Screen =
        ThirdScreen(arg)
}

class ThirdScreen(val arg: String) : Screen {

    override val key: ScreenKey
        get() = "${MyFlow.name}:${super.key}"

    @Composable
    override fun Content() {

        GenericScreen(
            title = "Third Screen",
            navIcon = Back,
            navAction = { VoyagerCommandDispatcher.dispatch(Pop) },
            genericAction = { VoyagerCommandDispatcher.dispatch(Open(FourthDestination)) },
            specialButtonText = "Result",
            specialAction = { StringResult.set("Third screen result") },
            topContent = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(arg)
                }
            }
        )
    }
}
