package com.bimboilya.firebase.navigation.chooser

import com.bimboilya.common.navigation.core.AppRouter
import com.bimboilya.firebase.feature.chooser.presentation.ChooserRouter
import com.bimboilya.firebase.navigation.config.ConfigDirection
import javax.inject.Inject

class ChooserRouterImpl @Inject constructor(
    private val appRouter: AppRouter
) : ChooserRouter {

    override fun openConfigScreen() {
        appRouter.open(ConfigDirection.route)
    }
}