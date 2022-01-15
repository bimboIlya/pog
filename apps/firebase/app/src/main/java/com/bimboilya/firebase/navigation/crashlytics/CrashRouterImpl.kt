package com.bimboilya.firebase.navigation.crashlytics

import com.bimboilya.common.navigation.voyager.AppRouter
import com.bimboilya.feature.crashlytics.main.presentation.CrashRouter
import com.bimboilya.feature.crashlytics.settings.CrashSettingsDestination
import javax.inject.Inject

class CrashRouterImpl @Inject constructor(
    private val appRouter: AppRouter
) : CrashRouter {

    override fun openSettings() {
        appRouter.open(CrashSettingsDestination())
    }
}