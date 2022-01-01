package com.bimboilya.firebase.navigation.chooser

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bimboilya.common.navigation.compose.ComposableDestination
import com.bimboilya.firebase.feature.chooser.ui.ChooserScreen

object ChooserDestination : ComposableDestination {

    @Composable
    override fun CreateComposable(backStackEntry: NavBackStackEntry) {
        ChooserScreen(viewModel = hiltViewModel())
    }
}