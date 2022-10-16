package com.bimboilya.dbpg

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DbpgApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initFlipper()
        initTimber()
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        AndroidFlipperClient.getInstance(this).apply {
            addPlugin(InspectorFlipperPlugin(this@DbpgApp, DescriptorMapping.withDefaults()))
            addPlugin(DatabasesFlipperPlugin(this@DbpgApp))
            addPlugin(SharedPreferencesFlipperPlugin(this@DbpgApp))
            addPlugin(NavigationFlipperPlugin.getInstance())
        }.start()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
