package com.bimboilya.firebase.feature.notification.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.hilt.getViewModel
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle
import com.bimboilya.common.ktx.compose.collectInComposition
import com.bimboilya.common.navigation.voyager.Destination
import com.bimboilya.common.navigation.voyager.VmScreen
import com.bimboilya.firebase.feature.notification.NotificationDestination
import com.bimboilya.firebase.feature.notification.presentation.NotificationViewModel
import com.bimboilya.firebase.feature.notification.presentation.State
import kotlin.reflect.KClass

class NotificationScreen : VmScreen() {
    override val associatedDestination: KClass<out Destination>
        get() = NotificationDestination::class

    @Composable
    override fun Content() {
        val viewModel = getViewModel<NotificationViewModel>()

        val context = LocalContext.current
        viewModel.newTokenEvent.collectInComposition { token ->
            Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
        }

        val state by viewModel.state.collectAsStateWithLifecycle()
        NotificationScreen(
            state = state,
            getToken = viewModel::getToken
        )
    }
}

@Composable
private fun NotificationScreen(
    state: State,
    getToken: () -> Unit,
) {
    Scaffold(topBar = { LoadingTopBar(state.loading) }) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Button(enabled = !state.loading, onClick = getToken) {
                Text("Get token")
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun LoadingTopBar(loading: Boolean, modifier: Modifier = Modifier) {
    Column(modifier) {
        TopAppBar(title = { Text("Notification") })
        AnimatedContent(targetState = loading) { visible ->
            when (visible) {
                true -> LinearProgressIndicator(Modifier.fillMaxWidth())
                false -> Spacer(Modifier.height(ProgressIndicatorDefaults.StrokeWidth))
            }
        }
    }
}
