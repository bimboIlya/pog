package com.bimboilya.bluepoop.shared.bluetooth.domain

import javax.inject.Inject

class GetBluetoothStateUseCase @Inject constructor(
  private val bluetoothStateRepository: BluetoothStateRepository,
) {

  operator fun invoke(): BluetoothState =
    bluetoothStateRepository.get()
}
