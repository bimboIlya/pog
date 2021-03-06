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
import cafe.adriel.voyager.hilt.getViewModel
import com.bimboilya.common.navigation.voyager.Destination
import com.bimboilya.common.navigation.voyager.VmScreen
import com.bimboilya.feature.crashlytics.settings.CrashSettingsDestination
import com.bimboilya.feature.crashlytics.settings.presentation.CrashSettingsViewModel
import kotlin.reflect.KClass

class CrashSettingsScreen : VmScreen() {
    override val associatedDestination: KClass<out Destination>
        get() = CrashSettingsDestination::class

    @Composable
    override fun Content() {
        val viewModel = getViewModel<CrashSettingsViewModel>()
        val isCrashLoggingEnabled by viewModel.crashLoggingEnabled.collectAsState(initial = false)

        CrashSettingsScreen(isCrashLoggingEnabled, onCrashSwitchCheckedChange = viewModel::setLoggingEnabled)
    }
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