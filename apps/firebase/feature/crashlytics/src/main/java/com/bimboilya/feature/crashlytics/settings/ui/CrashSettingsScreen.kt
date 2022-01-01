package com.bimboilya.feature.crashlytics.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bimboilya.feature.crashlytics.settings.presentation.CrashSettingsViewModel

@Composable
fun CrashSettingsScreen(viewModel: CrashSettingsViewModel) {
    val isCrashLoggingEnabled by viewModel.crashLoggingEnabled.collectAsState(initial = false)

    CrashSettingsScreen(isCrashLoggingEnabled, onCrashSwitchCheckedChange = viewModel::setLoggingEnabled)
}

@Composable
private fun CrashSettingsScreen(
    isCrashLoggingEnabled: Boolean,
    onCrashSwitchCheckedChange: (Boolean) -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Switch(
            checked = isCrashLoggingEnabled,
            onCheckedChange = onCrashSwitchCheckedChange
        )
        Text("Send logs to Crashlytics?")
    }
}