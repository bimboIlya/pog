package com.bimboilya.bluepoop.shared.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.getSystemService
import com.bimboilya.common.navigation.launcher.ActivityLauncher

val Context.isBluetoothAvailable: Boolean
  get() = getSystemService<BluetoothManager>() != null

val Context.bluetoothAdapter: BluetoothAdapter
  get() = getSystemService<BluetoothManager>()?.adapter ?: error("Bluetooth unsupported on this device")

/**
 * Requires bluetooth permission
 * @return boolean that indicates if bluetooth was enabled
 */
suspend fun enableBluetooth(): Boolean {
  val result = ActivityLauncher.launchAndAwaitResult(StartActivityForResult(), Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
  return result.resultCode == Activity.RESULT_OK
}

/**
 * Requires bluetooth permission
 * @return boolean that indicates if discoverable was enabled
 */
suspend fun enableDiscoverable(durationSec: Int = -1): Boolean {
  val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
    .apply { if (durationSec > 0) putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, durationSec) }
  val result = ActivityLauncher.launchAndAwaitResult(StartActivityForResult(), intent)
  return result.resultCode == Activity.RESULT_OK
}
