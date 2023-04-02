package com.bimboilya.bluepoop.feature.prerequisites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.bluepoop.feature.prerequisites.domain.RequiredBluetoothPermissions
import com.bimboilya.bluepoop.feature.prerequisites.domain.observeRequiredBluetoothPermissionsStates
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction.EnableBluetoothClicked
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction.OpenSettingsClicked
import com.bimboilya.bluepoop.feature.prerequisites.presentation.PrerequisitesAction.RequestPermissionsClicked
import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothState
import com.bimboilya.bluepoop.shared.bluetooth.domain.ObserveBluetoothStateUseCase
import com.bimboilya.bluepoop.shared.bluetooth.enableBluetooth
import com.bimboilya.common.permissions.PermissionManager
import com.bimboilya.common.permissions.PermissionState
import com.bimboilya.common.permissions.isGranted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrerequisitesViewModel @Inject constructor(
  observeBluetoothState: ObserveBluetoothStateUseCase,
  private val router: PrerequisitesRouter,
) : ViewModel() {

  private val initialState: PrerequisitesState
    get() = PrerequisitesState(
      permissionsState = PermissionsState.Denied(permanently = false),
      bluetoothState = BluetoothState.Off,
    )

  private val _state = MutableStateFlow(initialState)
  val state = _state.asStateFlow()

  init {
    combine(
      observeBluetoothState(),
      observeRequiredBluetoothPermissionsStates(),
      ::buildState
    )
      .onEach(_state::tryEmit)
      .launchIn(viewModelScope)
  }

  private fun buildState(bluetoothState: BluetoothState, bluetoothPermissionsStates: List<PermissionState>): PrerequisitesState =
    PrerequisitesState(
      permissionsState = buildPermissionsState(bluetoothPermissionsStates),
      bluetoothState = bluetoothState,
    )

  private fun buildPermissionsState(bluetoothPermissionsStates: List<PermissionState>): PermissionsState =
    when {
      bluetoothPermissionsStates.all(PermissionState::isGranted) -> PermissionsState.Granted
      bluetoothPermissionsStates.anyPermanentlyDenied() -> PermissionsState.Denied(permanently = true)
      else -> PermissionsState.Denied(permanently = false)
    }

  private fun List<PermissionState>.anyPermanentlyDenied(): Boolean =
    any { permissionState -> !permissionState.isGranted() && permissionState.isDeniedPermanently }

  fun handleAction(action: PrerequisitesAction) {
    when (action) {
      EnableBluetoothClicked -> onEnableBluetooth()
      RequestPermissionsClicked -> onRequestPermissions()
      OpenSettingsClicked -> router.openSettings()
    }
  }

  private fun onEnableBluetooth() {
    viewModelScope.launch {
      enableBluetooth()
    }
  }

  private fun onRequestPermissions() {
    viewModelScope.launch {
      PermissionManager.requestPermissions(RequiredBluetoothPermissions)
    }
  }
}
