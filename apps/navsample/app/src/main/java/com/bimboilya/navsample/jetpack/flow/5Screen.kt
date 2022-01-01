package com.bimboilya.navsample.jetpack.flow

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.jetpack.ComposableDestination
import com.bimboilya.navsample.jetpack.JetpackCommand.CloseFlow
import com.bimboilya.navsample.jetpack.JetpackCommand.Composite
import com.bimboilya.navsample.jetpack.JetpackCommand.Pop
import com.bimboilya.navsample.jetpack.JetpackCommand.PopUpToRoot
import com.bimboilya.navsample.jetpack.JetpackCommandDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

object FifthScreen : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        GenericScreen(
            title = "Fifth Screen",
            navIcon = Back,
            navAction = { JetpackCommandDispatcher.dispatch(Pop) },
            genericAction = { JetpackCommandDispatcher.dispatch(PopUpToRoot) },
            specialAction = {
                JetpackCommandDispatcher.dispatch(
                    Composite(
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