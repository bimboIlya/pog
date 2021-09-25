package com.bimboilya.firebase.navigation.chooser

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.compose.NavDirection
import com.bimboilya.firebase.feature.chooser.ui.ChooserScreen

object ChooserDirection : NavDirection {
    override val route = "${this::class}"

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        ChooserScreen()
    }
}