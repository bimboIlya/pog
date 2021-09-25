package com.bimboilya.firebase.navigation.crashlytics

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.compose.NavDirection
import com.bimboilya.feature.crashlytics.ui.CrashScreen

object CrashDirection : NavDirection {

    override val route = "${this::class}"

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        CrashScreen()
    }
}