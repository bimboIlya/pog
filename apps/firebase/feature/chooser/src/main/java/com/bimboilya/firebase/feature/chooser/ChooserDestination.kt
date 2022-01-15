package com.bimboilya.firebase.feature.chooser

import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.common.navigation.voyager.ScreenDestination
import com.bimboilya.firebase.feature.chooser.ui.ChooserScreen

class ChooserDestination : ScreenDestination {

    override fun createScreen(): Screen =
        ChooserScreen()
}