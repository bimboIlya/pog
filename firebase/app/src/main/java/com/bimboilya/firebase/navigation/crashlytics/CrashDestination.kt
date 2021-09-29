package com.bimboilya.firebase.navigation.crashlytics

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.compose.ComposableDestination
import com.bimboilya.feature.crashlytics.ui.CrashScreen

object CrashDestination : ComposableDestination {

    override val route = "${this::class}"

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        CrashScreen(viewModel = hiltViewModel())
    }
}