package com.bimboilya.feature.crashlytics.main.presentation

import androidx.lifecycle.ViewModel
import com.bimboilya.feature.crashlytics.ErrorLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CrashViewModel @Inject constructor(
    private val router: CrashRouter,
    private val errorLogger: ErrorLogger
) : ViewModel() {

    fun crash() {
        throw Exception()
    }

    fun logError() {
        errorLogger.log(RuntimeException("Non critical error"))
    }

    fun openSettings() {
        router.openSettings()
    }
}