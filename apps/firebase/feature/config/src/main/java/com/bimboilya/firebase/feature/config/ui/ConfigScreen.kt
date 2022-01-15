package com.bimboilya.firebase.feature.config.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.hilt.getViewModel
import com.bimboilya.common.navigation.voyager.Destination
import com.bimboilya.common.navigation.voyager.VmScreen
import com.bimboilya.firebase.feature.config.ConfigDestination
import com.bimboilya.firebase.feature.config.presentation.ConfigViewModel
import kotlin.reflect.KClass

class ConfigScreen : VmScreen() {
    override val associatedDestination: KClass<out Destination>
        get() = ConfigDestination::class

    @Composable
    override fun Content() {
        val viewModel = getViewModel<ConfigViewModel>()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Config Screen")
        }
    }
}
