package com.bimboilya.feature.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class ErrorLogger @Inject constructor() {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    fun log(error: Throwable) {
        crashlytics.recordException(error)
    }
}