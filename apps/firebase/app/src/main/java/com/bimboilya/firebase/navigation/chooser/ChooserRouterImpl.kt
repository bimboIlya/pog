package com.bimboilya.firebase.navigation.chooser

import com.bimboilya.common.navigation.voyager.AppRouter
import com.bimboilya.feature.crashlytics.CrashDestination
import com.bimboilya.firebase.feature.chooser.presentation.ChooserRouter
import com.bimboilya.firebase.feature.config.ConfigDestination
import com.bimboilya.firebase.feature.firestore.FirestoreDestination
import javax.inject.Inject

class ChooserRouterImpl @Inject constructor(
    private val appRouter: AppRouter
) : ChooserRouter {

    override fun openConfigScreen() {
        appRouter.open(ConfigDestination())
    }

    override fun openCrashScreen() {
        appRouter.open(CrashDestination())
    }

    override fun openFirestoreScreen() {
        appRouter.open(FirestoreDestination())
    }
}