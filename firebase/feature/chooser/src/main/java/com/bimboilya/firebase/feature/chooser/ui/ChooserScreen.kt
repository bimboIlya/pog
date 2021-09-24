package com.bimboilya.firebase.feature.chooser.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.bimboilya.firebase.feature.chooser.presentation.ChooserViewModel

@Composable
fun ChooserScreen(viewModel: ChooserViewModel = hiltViewModel()) {
    ChooserScreen(
        viewModel::openConfigScreen,
    )
}

@Composable
private fun ChooserScreen(
    openConfigScreen: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = openConfigScreen) {
            Text(text = "Config Screen")
        }
    }
}