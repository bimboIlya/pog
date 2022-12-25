package com.bimboilya.navsample.jetpack.flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
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
import com.bimboilya.common.ktx.android.getObjectOrThrow
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle
import com.bimboilya.navsample.common.GenericScreen
import com.bimboilya.navsample.common.NavIcon.Back
import com.bimboilya.navsample.common.SongResult
import com.bimboilya.navsample.common.StringResult
import com.bimboilya.navsample.jetpack.ComposableWithArgsDestination
import com.bimboilya.navsample.jetpack.JetpackCommand.Open
import com.bimboilya.navsample.jetpack.JetpackCommand.Pop
import com.bimboilya.navsample.jetpack.JetpackCommandDispatcher
import com.bimboilya.navsample.jetpack.navArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

object ThirdScreen : ComposableWithArgsDestination {

    fun createRoute(song: Song): String {
        val songJson = Json.encodeToString(song)
        return "${javaClass.simpleName}/$songJson"
    }

    override fun getRoute(): String =
        "${javaClass.simpleName}/{$SongKey}"

    override fun getArguments(): List<NamedNavArgument> =
        navArgument<String>(SongKey)

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        val vm: VM = hiltViewModel()
        StringResult.observe(onResult = vm.result::value::set)

        GenericScreen(
            title = "Third Screen",
            navIcon = Back,
            navAction = { JetpackCommandDispatcher.dispatch(Pop) },
            genericAction = { JetpackCommandDispatcher.dispatch(Open(FourthScreen)) },
            topContent = {
                val song by vm.flow.collectAsStateWithLifecycle()
                Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${song.id} ${song.artist} ${song.album} ${song.name}")
                        Button(onClick = {
                            val result = Song(id = 3, artist = "Third", album = "Screen", name = "Result")
                            SongResult.set(result) }) {
                            Text("Result")
                        }
                    }
                }
            },
            bottomContent = {
                val result by vm.result.collectAsStateWithLifecycle()
                Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                    result?.let { Text(it) }
                }
            }
        )
    }
}

const val SongKey = "SongKey"

@Serializable
data class Song(
    val id: Long,
    val artist: String,
    val album: String,
    val name: String,
)

@HiltViewModel
class VM @Inject constructor(stateHandle: SavedStateHandle) : ViewModel() {

    init {
        Timber.d("$SongKey contains→ ${stateHandle.get<String>(SongKey)}")
    }

    val flow = MutableStateFlow<Song>(stateHandle.getObjectOrThrow(SongKey))

    val result = MutableStateFlow<String?>(null)
}
