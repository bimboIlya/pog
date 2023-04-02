package com.bimboilya.bluepoop.shared.bluetooth.domain

import kotlinx.coroutines.flow.Flow

interface BluetoothStateRepository {
  fun get(): BluetoothState
  fun observe(): Flow<BluetoothState>
}
