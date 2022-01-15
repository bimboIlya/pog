package com.bimboilya.feature.crashlytics

import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.common.navigation.voyager.ScreenDestination
import com.bimboilya.feature.crashlytics.main.ui.CrashScreen

class CrashDestination : ScreenDestination {

    override fun createScreen(): Screen =
        CrashScreen()
}