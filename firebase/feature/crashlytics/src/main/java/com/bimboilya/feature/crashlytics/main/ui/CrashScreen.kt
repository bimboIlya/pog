package com.bimboilya.feature.crashlytics.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bimboilya.feature.crashlytics.main.presentation.CrashViewModel

@Composable
fun CrashScreen(viewModel: CrashViewModel) {
    CrashScreen(
        onCrashClick = viewModel::crash,
        onLogErrorClick = viewModel::logError,
        onSettingsClick = viewModel::openSettings
    )
}

@Composable
private fun CrashScreen(
    onCrashClick: () -> Unit,
    onLogErrorClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = onSettingsClick) {
            Image(Icons.Default.Settings, contentDescription = null)
        }
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onCrashClick) {
            Text(text = "Crash")
        }

        Button(onClick = onLogErrorClick) {
            Text(text = "Log error")
        }
    }
}