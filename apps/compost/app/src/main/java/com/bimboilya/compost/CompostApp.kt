package com.bimboilya.compost

import android.app.Application
import timber.log.Timber

class CompostApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
