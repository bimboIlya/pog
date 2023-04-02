package com.bimboilya.bluepoop.shared.bluetooth.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBluetoothStateUseCase @Inject constructor(
  private val bluetoothStateRepository: BluetoothStateRepository,
) {

  operator fun invoke(): Flow<BluetoothState> =
    bluetoothStateRepository.observe()
}
