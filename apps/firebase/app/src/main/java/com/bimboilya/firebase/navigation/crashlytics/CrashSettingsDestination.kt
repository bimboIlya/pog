package com.bimboilya.firebase.navigation.crashlytics

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.jetpack.ComposableDestination
import com.bimboilya.feature.crashlytics.settings.ui.CrashSettingsScreen

object CrashSettingsDestination : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        CrashSettingsScreen(viewModel = hiltViewModel())
    }
}