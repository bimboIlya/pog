package com.bimboilya.authist

import android.app.Application
import com.bimboilya.common.navigation.launcher.ActivityProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AuthApp : Application() {

    @Inject
    lateinit var activityProvider: ActivityProvider

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initActivityProvider()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initActivityProvider() {
        activityProvider.init(this)
    }
}
