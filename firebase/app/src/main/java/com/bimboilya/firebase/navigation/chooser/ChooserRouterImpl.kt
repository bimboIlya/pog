package com.bimboilya.firebase.navigation.chooser

import com.bimboilya.common.navigation.core.AppRouter
import com.bimboilya.firebase.feature.chooser.presentation.ChooserRouter
import com.bimboilya.firebase.navigation.config.ConfigDirection
import com.bimboilya.firebase.navigation.crashlytics.CrashDirection
import javax.inject.Inject

class ChooserRouterImpl @Inject constructor(
    private val appRouter: AppRouter
) : ChooserRouter {

    override fun openConfigScreen() {
        appRouter.open(ConfigDirection.route)
    }

    override fun openCrashScreen() {
        appRouter.open(CrashDirection.route)
    }
}