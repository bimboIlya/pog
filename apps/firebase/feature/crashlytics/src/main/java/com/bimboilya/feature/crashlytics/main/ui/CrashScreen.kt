package com.bimboilya.feature.crashlytics.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.hilt.getViewModel
import com.bimboilya.common.navigation.voyager.Destination
import com.bimboilya.common.navigation.voyager.VmScreen
import com.bimboilya.feature.crashlytics.CrashDestination
import com.bimboilya.feature.crashlytics.main.presentation.CrashViewModel
import kotlin.reflect.KClass

class CrashScreen : VmScreen() {
    override val associatedDestination: KClass<out Destination>
        get() = CrashDestination::class

    @Composable
    override fun Content() {
        val viewModel = getViewModel<CrashViewModel>()

        CrashScreen(
            onCrashClick = viewModel::crash,
            onLogErrorClick = viewModel::logError,
            onSettingsClick = viewModel::openSettings
        )
    }
}

@Composable
private fun CrashScreen(
    onCrashClick: () -> Unit,
    onLogErrorClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = onSettingsClick) {
            Icon(Icons.Default.Settings, contentDescription = null)
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