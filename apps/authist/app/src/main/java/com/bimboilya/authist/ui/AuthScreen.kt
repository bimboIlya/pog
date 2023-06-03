package com.bimboilya.authist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bimboilya.authist.domain.entity.SocialNetwork
import com.bimboilya.authist.domain.entity.SocialNetwork.GOOGLE
import com.bimboilya.authist.domain.entity.SocialNetwork.VK
import com.bimboilya.authist.domain.entity.SocialNetwork.YANDEX
import com.bimboilya.authist.domain.entity.User
import com.bimboilya.authist.presentation.AuthState
import com.bimboilya.authist.presentation.AuthViewModel
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle
import com.bimboilya.common.ktx.compose.collectInComposition
import com.bimboilya.common.ui.components.LoadingTopBar
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates.notNull

@Composable
fun AuthScreen() {
    val viewModel: AuthViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    // that's fucking retarded; just hoist scaffold state
    var showSnackbar: (String) -> Unit by notNull()
    viewModel.event.collectInComposition { message ->
        showSnackbar(message)
    }

    AuthScreen(
        state = state,
        signIn = viewModel::signIn,
        snackbarCallbackProvider = { showSnackbar = it }
    )
}

@Composable
private fun AuthScreen(
    state: AuthState,
    signIn: (SocialNetwork) -> Unit,
    snackbarCallbackProvider: ((String) -> Unit) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    snackbarCallbackProvider { message ->
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { LoadingTopBar(state.isLoading, title = { Text("Notification") }) },
        bottomBar = { BottomBar(state.isLoading, signIn) }
    ) { padding ->
        Content(user = state.user, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun BottomBar(isLoading: Boolean, signIn: (SocialNetwork) -> Unit) {
    Surface(
        Modifier.fillMaxWidth(),
        elevation = 8.dp
    ) {
        FlowRow(
            Modifier.fillMaxWidth().padding(4.dp),
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            mainAxisSpacing = 8.dp,
        ) {
            Button(onClick = { signIn(GOOGLE) }, enabled = !isLoading) {
                Text("Google SignIn")
            }
            Button(onClick = { signIn(VK) }, enabled = !isLoading) {
                Text("VK SignIn")
            }
            Button(onClick = { signIn(YANDEX) }, enabled = !isLoading) {
                Text("Yandex SignIn")
            }
        }
    }
}

@Composable
private fun Content(user: User?, modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (user == null) {
            Text(text = "Nothing here")
        } else {
            val text = with(user) {
                """
                    id = $id
                    name = $name
                    email = $email
                    token = $token
                """.trimIndent()
            }
            Text(text, Modifier.padding(16.dp))
        }
    }
}
