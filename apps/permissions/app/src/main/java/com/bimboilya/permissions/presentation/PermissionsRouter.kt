package com.bimboilya.permissions.presentation

import androidx.activity.ComponentActivity
import com.bimboilya.common.ktx.android.openSettings
import com.bimboilya.common.navigation.launcher.ActivityProvider
import javax.inject.Inject

class PermissionsRouter @Inject constructor(
    private val activityProvider: ActivityProvider,
) {

    private val activity: ComponentActivity?
        get() = activityProvider.activity

    fun openSettings() {
        activity?.openSettings()
    }
}
