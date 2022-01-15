package com.bimboilya.firebase.feature.config

import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.common.navigation.voyager.ScreenDestination
import com.bimboilya.firebase.feature.config.ui.ConfigScreen

class ConfigDestination : ScreenDestination {

    override fun createScreen(): Screen =
        ConfigScreen()
}