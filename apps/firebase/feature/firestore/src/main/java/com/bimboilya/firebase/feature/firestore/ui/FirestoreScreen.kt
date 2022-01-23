package com.bimboilya.firebase.feature.firestore.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope.SlideDirection
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import com.bimboilya.common.ktx.android.collectAsStateWithLifecycle
import com.bimboilya.common.navigation.voyager.Destination
import com.bimboilya.common.navigation.voyager.VmScreen
import com.bimboilya.firebase.feature.firestore.FirestoreDestination
import com.bimboilya.firebase.feature.firestore.data.Test
import com.bimboilya.firebase.feature.firestore.presentation.FirestoreViewModel
import com.bimboilya.firebase.feature.firestore.presentation.State
import kotlin.reflect.KClass

class FirestoreScreen : VmScreen() {
    override val associatedDestination: KClass<out Destination>
        get() = FirestoreDestination::class

    @Composable
    override fun Content() {
        val context = LocalContext.current
        DisposableEffect(Unit) {
            val startingOrientation = (context as? Activity)?.requestedOrientation
            (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            onDispose {
                @SuppressLint("SourceLockedOrientationActivity")
                (context as? Activity)?.requestedOrientation = requireNotNull(startingOrientation)
            }
        }

        val viewModel: FirestoreViewModel = getViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        FirestoreScreen(state, viewModel::load, viewModel::addRandom)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FirestoreScreen(
    state: State,
    loadData: () -> Unit,
    addRandom: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Firestore") }
            )
        },
        bottomBar = {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    slideIntoContainer(SlideDirection.Up) with slideOutOfContainer(SlideDirection.Down)
                }
            ) { state ->
                when {
                    state.loading -> Box(Modifier.fillMaxWidth().height(56.dp))
                    else -> BottomBar(loadData, addRandom)
                }
            }
        }
    ) { padding ->
        FadeVisibility(visible = state.data.isNotEmpty()) {
            Content(padding, state.data)
        }
        FadeVisibility(visible = state.loading) {
            Loading(padding)
        }
    }
}

@Composable
private fun FadeVisibility(visible: Boolean, modifier: Modifier = Modifier, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut(),
        content = content,
    )
}

@Composable
private fun Content(padding: PaddingValues, data: List<Test>) {
    LazyColumn(Modifier.padding(padding).fillMaxSize()) {
        items(data) { test ->
            Card(Modifier.padding(8.dp).fillMaxWidth()) {
                Text(test.toString(), Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
private fun BottomBar(loadData: () -> Unit, addRandom: () -> Unit) {
    BottomAppBar {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = loadData) {
                Text("Load")
            }
            Button(onClick = addRandom) {
                Text("Add")
            }
        }
    }
}

@Composable
private fun Loading(padding: PaddingValues) {
    Box(
        Modifier.padding(padding).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}