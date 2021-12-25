package com.bimboilya.navsample.jetpack.flow

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.common.navigation.Command
import com.bimboilya.navsample.common.navigation.Command.CloseFlow
import com.bimboilya.navsample.common.navigation.Command.Pop
import com.bimboilya.navsample.common.navigation.Command.PopUpToRoot
import com.bimboilya.navsample.common.navigation.CommandDispatcher
import com.bimboilya.navsample.jetpack.ComposableDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

object FifthScreen : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        GenericScreen(
            title = "Fifth Screen",
            navIcon = Back,
            navAction = { CommandDispatcher.dispatch(Pop) },
            genericAction = { CommandDispatcher.dispatch(PopUpToRoot) },
            specialAction = {
                CommandDispatcher.dispatch(
                    Command.Composite(
                        listOf(
                            CloseFlow,
                        )
                    )
                )
            }
        )
    }
}


@HiltViewModel
class FifthViewModel @Inject constructor() : ViewModel() {

    init {
        Timber.d("FifthViewModel initialized→")
    }

    override fun onCleared() {
        Timber.d("FifthViewModel cleared→")
    }
}