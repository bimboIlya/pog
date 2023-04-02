package com.bimboilya.bluepoop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.bluepoop.feature.prerequisites.domain.observeRequiredBluetoothPermissionsStates
import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothState
import com.bimboilya.bluepoop.shared.bluetooth.domain.ObserveBluetoothStateUseCase
import com.bimboilya.bluepoop.shared.navigation.CommandDispatcher
import com.bimboilya.common.permissions.PermissionState
import com.bimboilya.common.permissions.isGranted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BluepoopViewModel @Inject constructor(
  observeBluetoothState: ObserveBluetoothStateUseCase,
  commandDispatcher: CommandDispatcher,
) : ViewModel() {

  private val initialState: MainState
    get() = MainState(isPrerequisitesVisible = false)

  private val _state = MutableStateFlow(initialState)
  val state = _state.asStateFlow()

  val navigationCommands = commandDispatcher.observe()

  init {
    combine(
      observeBluetoothState(),
      observeRequiredBluetoothPermissionsStates(),
      ::buildState
    )
      .onEach(_state::tryEmit)
      .launchIn(viewModelScope)
  }

  private fun buildState(bluetoothState: BluetoothState, bluetoothPermissionsStates: List<PermissionState>): MainState =
    MainState(isPrerequisitesVisible = !bluetoothState.enabled || bluetoothPermissionsStates.none(PermissionState::isGranted))
}

data class MainState(
  val isPrerequisitesVisible: Boolean,
)
