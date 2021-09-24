package com.bimboilya.firebase.feature.config.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bimboilya.firebase.feature.config.presentation.ConfigViewModel

@Composable
fun ConfigScreen(viewModel: ConfigViewModel = hiltViewModel()) {
    ConfigScreen()
}

@Composable
private fun ConfigScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Config Screen")
    }
}