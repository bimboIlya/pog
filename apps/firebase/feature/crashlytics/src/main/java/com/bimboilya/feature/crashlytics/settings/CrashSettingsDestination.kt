package com.bimboilya.feature.crashlytics.settings

import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.common.navigation.voyager.ScreenDestination
import com.bimboilya.feature.crashlytics.settings.ui.CrashSettingsScreen

class CrashSettingsDestination : ScreenDestination {

    override fun createScreen(): Screen =
        CrashSettingsScreen()
}