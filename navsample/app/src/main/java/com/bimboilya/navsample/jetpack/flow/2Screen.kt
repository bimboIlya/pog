package com.bimboilya.navsample.jetpack.flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.ktx.android.collectAsStateWithLifecycle
import com.bimboilya.common.ktx.android.getOrDefault
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.common.navigation.Command.Open
import com.bimboilya.navsample.common.navigation.Command.OpenRoute
import com.bimboilya.navsample.common.navigation.Command.Pop
import com.bimboilya.navsample.common.navigation.CommandDispatcher
import com.bimboilya.navsample.common.navigation.SongResult
import com.bimboilya.navsample.jetpack.ComposableWithArgsDestination
import com.bimboilya.navsample.jetpack.navArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

object SecondScreen : ComposableWithArgsDestination {

    fun createRoute(arg: String? = null): String =
        javaClass.simpleName
            .run {
                if (arg != null) {
                    plus("?$ARG_KEY=$arg")
                } else this
            }

    override fun getRoute(): String =
        "${javaClass.simpleName}?$ARG_KEY={$ARG_KEY}"

    override fun getArguments(): List<NamedNavArgument> =
        navArgument<String>(ARG_KEY, isNullable = true)

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        val viewModel: SecondViewModel = hiltViewModel()

        SongResult.observe(onResult = viewModel.result::value::set)

        GenericScreen(
            title = "Second Screen",
            navIcon = Back,
            navAction = { CommandDispatcher.dispatch(Pop) },
            genericAction = {
                val song = Song(42, artist = "Nirvana", "Nevermind", "Poop")
                CommandDispatcher.dispatch(OpenRoute(ThirdScreen.createRoute(song)))
            },
            specialAction = { CommandDispatcher.dispatch(Open(ThirdScreen)) },
            specialButtonText = "With delay",
            topContent = {
                val str by viewModel.flow.collectAsStateWithLifecycle()
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(str)
                }
            },
            bottomContent = {
                val result by viewModel.result.collectAsStateWithLifecycle()
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    result?.let { Text(it.toString()) }
                }
            }
        )
    }
}

const val ARG_KEY = "ARG_KEY"

@HiltViewModel
class SecondViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
) : ViewModel() {

    val flow = MutableStateFlow(stateHandle.getOrDefault(ARG_KEY, "default"))

    val result = MutableStateFlow<Song?>(null)

    init {
        Timber.d("CONTAINS ARG_KEY→ ${stateHandle.get<String>(ARG_KEY)}")
    }
}
