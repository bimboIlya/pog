package com.bimboilya.bluepoop

import android.app.Application
import com.bimboilya.bluepoop.bluetooth.isBluetoothAvailable
import com.bimboilya.common.ktx.android.shortToast
import com.bimboilya.common.navigation.launcher.ActivityProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BluepoopApp : Application() {

    @Inject
    lateinit var activityProvider: ActivityProvider

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
        activityProvider.init(this)
    }
}
