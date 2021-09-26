package com.bimboilya.firebase.feature.chooser.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bimboilya.firebase.feature.chooser.presentation.ChooserViewModel

@Composable
fun ChooserScreen(viewModel: ChooserViewModel = hiltViewModel()) {
    ChooserScreen(
        viewModel::openConfigScreen,
        viewModel::openCrashScreen,
    )
}

@Composable
private fun ChooserScreen(
    onConfigClick: () -> Unit,
    onCrashClick: () ->Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onConfigClick) {
            Text(text = "Config Screen")
        }
        Button(onClick = onCrashClick) {
            Text(text = "Crashlytics Screen")
        }
    }
}