package com.bimboilya.permissions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle
import com.bimboilya.common.permissions.isGranted
import com.bimboilya.permissions.presentation.PermissionsViewModel
import com.bimboilya.permissions.presentation.SettingsDialogState

@Composable
fun PermissionsScreen() {
    val viewModel: PermissionsViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.settingsDialogState !is SettingsDialogState.Hidden) {
                SettingsDialog(
                    rationale = state.settingsDialogState.rationale,
                    dismiss = viewModel::dismissSettingsDialog,
                    openSettings = viewModel::openSettings,
                )
            }

            state.permissionStates.forEach {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { viewModel.requestPermission(it.permission) }) {
                        val icon = if (it.isGranted()) "✓ " else "x "
                        Text(text = icon + it.permission.name)
                    }
                    if (!it.isGranted()) {
                        val text = when {
                            it.shouldShowRationale -> "bro blease, ${it.permission.name} is a must have"
                            it.isDeniedPermanently -> "open settings retard"
                            else -> "never denied nor granted"
                        }
                        Text(
                            text = text,
                            modifier = Modifier.padding(top = 4.dp),
                            style = MaterialTheme.typography.subtitle2,
                        )
                    }
                }
            }

            Button(onClick = viewModel::requestMultiple) {
                Text(text = "Multiple")
            }
        }
    }
}

@Composable
private fun SettingsDialog(rationale: String, dismiss: () -> Unit, openSettings: () -> Unit) {
    AlertDialog(
        onDismissRequest = dismiss,
        confirmButton = {
            TextButton(onClick = openSettings) {
                Text(text = "Settings")
            }
        },
        modifier = Modifier.padding(4.dp),
        dismissButton = {
            TextButton(onClick = dismiss) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "bro blease", style = MaterialTheme.typography.h4) },
        text = { Text(text = rationale) },
        shape = RoundedCornerShape(16.dp),
    )
}
