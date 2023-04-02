package com.bimboilya.bluepoop

import android.app.Application
import com.bimboilya.bluepoop.shared.bluetooth.isBluetoothAvailable
import com.bimboilya.common.ktx.android.shortToast
import com.bimboilya.common.navigation.launcher.ActivityProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BluepoopApp : Application() {

  override fun onCreate() {
    super.onCreate()

    checkBluetooth()

    initTimber()
    initActivityProvider()
  }

  private fun checkBluetooth() {
    if (!isBluetoothAvailable) {
      shortToast("Device doesn't support bluetooth")
      throw Exception("Death")
    }
  }

  private fun initTimber() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initActivityProvider() {
    ActivityProvider.init(this)
  }
}
