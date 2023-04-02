package com.bimboilya.bluepoop.feature.prerequisites.presentation

import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothState

data class PrerequisitesState(
  val bluetoothState: BluetoothState,
  val permissionsState: PermissionsState,
)

sealed interface PermissionsState {
  object Granted : PermissionsState
  data class Denied(val permanently: Boolean) : PermissionsState
}
