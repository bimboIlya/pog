package com.bimboilya.permissions

import android.app.Application
import com.bimboilya.common.navigation.launcher.ActivityProvider
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class PermissionsApp : Application() {

    @Inject lateinit var activityProvider: ActivityProvider

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initActivityProvider()
        initFlipper()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initActivityProvider() {
        activityProvider.init(this)
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        AndroidFlipperClient.getInstance(this).apply {
            addPlugin(InspectorFlipperPlugin(this@PermissionsApp, DescriptorMapping.withDefaults()))
            addPlugin(SharedPreferencesFlipperPlugin(this@PermissionsApp))
        }.start()
    }
}
