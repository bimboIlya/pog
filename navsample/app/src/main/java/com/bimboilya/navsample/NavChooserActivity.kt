package com.bimboilya.navsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bimboilya.common.ktx.android.startActivity
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.navsample.common.auth.AuthInteractor
import com.bimboilya.navsample.jetpack.JetpackActivity
import com.bimboilya.navsample.voyager.VoyagerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NavChooserActivity : ComponentActivity() {

    @Inject
    lateinit var authInteractor: AuthInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isAuthorized by authInteractor.observeAuthorized().collectAsState(initial = false)
            val scope = rememberCoroutineScope()

            PogTheme {
                Surface {
                    Content(
                        isAuthorized,
                        onSetAuthorized = { authorized ->
                            scope.launch { authInteractor.setAuthorized(authorized) }
                        },
                        onOpenJetpackFlow = { startActivity<JetpackActivity>() },
                        onOpenVoyagerFlow = { startActivity<VoyagerActivity>() },
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    isAuthorized: Boolean,
    onSetAuthorized: (Boolean) -> Unit,
    onOpenJetpackFlow: () -> Unit,
    onOpenVoyagerFlow: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onOpenJetpackFlow) {
            Text(text = "Jetpack Navigation")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isAuthorized,
                onCheckedChange = onSetAuthorized
            )
            Spacer(Modifier.width(8.dp))
            Text(text = "Is user authorized")
        }
        Button(onClick = onOpenVoyagerFlow) {
            Text(text = "Voyager")
        }
    }
}