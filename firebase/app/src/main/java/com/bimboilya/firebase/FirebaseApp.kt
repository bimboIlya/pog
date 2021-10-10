package com.bimboilya.firebase

import android.app.Application
import com.bimboilya.firebase.util.DataCollectionHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class FirebaseApp : Application() {

    @Inject
    lateinit var dataCollectionHelper: DataCollectionHelper

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}