package com.bimboilya.bluepoop.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService

val Context.isBluetoothAvailable: Boolean
    get() = getSystemService<BluetoothManager>() != null

val Context.bluetoothAdapter: BluetoothAdapter
    get() = getSystemService<BluetoothManager>()?.adapter ?: error("Bluetooth unsupported on this device")

@SuppressLint("MissingPermission")
fun Activity.requestBluetooth() {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    startActivityForResult(intent, 123)
}

@SuppressLint("MissingPermission")
fun Activity.requestDiscoverable(durationSeconds: Int = 60) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        .apply { putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, durationSeconds) }
    startActivityForResult(intent, 321)
}
