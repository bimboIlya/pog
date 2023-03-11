package com.bimboilya.authist

import android.app.Application
import com.bimboilya.common.navigation.launcher.ActivityProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AuthApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initActivityProvider()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initActivityProvider() {
        ActivityProvider.init(this)
    }
}
