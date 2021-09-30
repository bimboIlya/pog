package com.bimboilya.feature.crashlytics.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bimboilya.feature.crashlytics.main.presentation.CrashViewModel

@Composable
fun CrashScreen(viewModel: CrashViewModel) {
    CrashScreen(
        onCrashClick = viewModel::crash,
        onLogErrorClick = viewModel::logError,
    )
}

@Composable
private fun CrashScreen(
    onCrashClick: () -> Unit,
    onLogErrorClick: () -> Unit,
) {
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