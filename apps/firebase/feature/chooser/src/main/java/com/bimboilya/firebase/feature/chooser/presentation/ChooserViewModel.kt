package com.bimboilya.firebase.feature.chooser.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooserViewModel @Inject constructor(
    private val router: ChooserRouter
) : ViewModel() {

    fun openConfigScreen() {
        router.openConfigScreen()
    }

    fun openCrashScreen() {
        router.openCrashScreen()
    }

    fun openFirestoreScreen() {
        router.openFirestoreScreen()
    }

    fun openNotificationScreen() {
        router.openNotificationScreen()
    }
}