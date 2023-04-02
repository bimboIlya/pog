package com.bimboilya.bluepoop.feature.prerequisites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PermissionsState.Denied
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction.EnableBluetoothClicked
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction.OpenSettingsClicked
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction.RequestPermissionsClicked
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesState
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesViewModel
import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothState
import com.bimboilya.bluepoop.shared.bluetooth.domain.description
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle

@Composable
fun PrerequisitesBottomSheet() {
  // vm is scoped to activity which is not good but I don't care
  val viewModel: PrerequisitesViewModel = viewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  PrerequisitesContent(
    state = state,
    onAction = viewModel::handleAction,
  )
}

@Composable
private fun PrerequisitesContent(
  state: PrerequisitesState,
  onAction: (PrerequisitesAction) -> Unit,
) {
  Column(Modifier.fillMaxHeight(fraction = 0.97f).fillMaxWidth()) {
    Text(
      text = "To use this app you have to grant required permissions and enable bluetooth",
      modifier = Modifier.padding(12.dp),
      textAlign = TextAlign.Center,
    )
    Divider(Modifier.padding(bottom = 12.dp))
    if (state.permissionsState is Denied)
      DeniedPermissionsState(state.permissionsState.permanently, onAction)
    else
      BluetoothState(state.bluetoothState, onAction)
  }
}

@Composable
private fun DeniedPermissionsState(
  isPermanentlyDenied: Boolean,
  onAction: (PrerequisitesAction) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    val text = if (isPermanentlyDenied) "Open settings" else "Request permissions"
    val action = if (isPermanentlyDenied) OpenSettingsClicked else RequestPermissionsClicked

    Button(onClick = { onAction(action) }) {
      Text(text)
    }
  }
}

@Composable
private fun BluetoothState(
  state: BluetoothState,
  onAction: (PrerequisitesAction) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize().padding(vertical = 8.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("Bluetooth is: ${state.description}", Modifier.padding(horizontal = 12.dp).padding(bottom = 8.dp))

    when (state) {
      BluetoothState.Off,
      BluetoothState.Error,
      BluetoothState.Unknown -> EnableBluetoothButton(onClick = { onAction(EnableBluetoothClicked) })

      BluetoothState.On -> Text("All good mate")

      BluetoothState.TurningOff,
      BluetoothState.TurningOn -> CircularProgressIndicator()

    }
  }
}

@Composable
private fun EnableBluetoothButton(onClick: () -> Unit) {
  Button(onClick) {
    Text("Enable BT")
  }
}
