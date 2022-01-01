package com.bimboilya.navsample.voyager.flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.common.StringResult
import com.bimboilya.navsample.voyager.Open
import com.bimboilya.navsample.voyager.Pop
import com.bimboilya.navsample.voyager.ScreenDestination
import com.bimboilya.navsample.voyager.VoyagerCommandDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

object SecondDestination : ScreenDestination {

    override fun createScreen(): Screen =
        SecondScreen()
}

class SecondScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel: SecondScreenModel = getScreenModel()

        GenericScreen(
            title = "Second Screen",
            navIcon = Back,
            navAction = { VoyagerCommandDispatcher.dispatch(Pop) },
            genericAction = { VoyagerCommandDispatcher.dispatch(Open(ThirdDestination("welp"))) },
            specialButtonText = "With delay",
            specialAction = screenModel::openThirdWithDelay,
            topContent = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    var text by remember { mutableStateOf("") }
                    StringResult.observe { result -> text = result }

                    Text(text)
                }
            }
        )
    }
}

class SecondScreenModel @Inject constructor() : ScreenModel {

    fun openThirdWithDelay() {
        coroutineScope.launch {
            delay(3000)
            VoyagerCommandDispatcher.dispatch(Open(ThirdDestination("delayed")))
        }
    }

    override fun onDispose() {
        Timber.d("Second disposed →")
    }
}
