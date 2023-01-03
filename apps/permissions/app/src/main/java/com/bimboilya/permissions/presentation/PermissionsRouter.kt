package com.bimboilya.permissions.presentation

import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.ComponentActivity
import com.bimboilya.common.navigation.launcher.ActivityProvider
import javax.inject.Inject

class PermissionsRouter @Inject constructor(
    private val activityProvider: ActivityProvider,
) {

    private val activity: ComponentActivity?
        get() = activityProvider.activity

    fun openSettings() {
        activity?.let { activity ->
            Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                .apply { data = Uri.parse("package:${activity.packageName}") }
                .let(activity::startActivity)
        }
    }
}
