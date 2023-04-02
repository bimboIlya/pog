package com.bimboilya.bluepoop.shared.bluetooth.domain

import android.bluetooth.BluetoothAdapter

sealed interface BluetoothState {
  object Off : BluetoothState
  object On : BluetoothState
  object TurningOff : BluetoothState
  object TurningOn : BluetoothState
  object Error : BluetoothState
  object Unknown : BluetoothState

  val enabled: Boolean get() = this is On
}

fun Int.toBluetoothState(): BluetoothState =
  when (this) {
    BluetoothAdapter.STATE_OFF -> BluetoothState.Off
    BluetoothAdapter.STATE_ON -> BluetoothState.On
    BluetoothAdapter.STATE_TURNING_OFF -> BluetoothState.TurningOff
    BluetoothAdapter.STATE_TURNING_ON -> BluetoothState.TurningOn
    BluetoothAdapter.ERROR -> BluetoothState.Error
    else -> BluetoothState.Unknown
  }

val BluetoothState.description: String
  get() = when (this) {
    BluetoothState.Off -> "Off"
    BluetoothState.On -> "On"
    BluetoothState.TurningOff -> "Turning off"
    BluetoothState.TurningOn -> "Turning on"
    BluetoothState.Error -> "Error"
    BluetoothState.Unknown -> "Unknown"
  }
