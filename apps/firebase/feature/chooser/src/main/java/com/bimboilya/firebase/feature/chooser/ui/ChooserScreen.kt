package com.bimboilya.firebase.feature.chooser.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.hilt.getViewModel
import com.bimboilya.common.navigation.voyager.Destination
import com.bimboilya.common.navigation.voyager.VmScreen
import com.bimboilya.firebase.feature.chooser.ChooserDestination
import com.bimboilya.firebase.feature.chooser.presentation.ChooserViewModel
import kotlin.reflect.KClass

class ChooserScreen : VmScreen() {
    override val associatedDestination: KClass<out Destination>
        get() = ChooserDestination::class

    @Composable
    override fun Content() {
        val viewModel = getViewModel<ChooserViewModel>()

        ChooserScreen(
            viewModel::openConfigScreen,
            viewModel::openCrashScreen,
            viewModel::openFirestoreScreen,
            viewModel::openNotificationScreen,
        )
    }

}

@Composable
private fun ChooserScreen(
    openConfigScreen: () -> Unit,
    openCrashScreen: () -> Unit,
    openFirestoreScreen: () -> Unit,
    openNotificationScreen: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = openConfigScreen) {
            Text(text = "Config Screen")
        }
        Button(onClick = openCrashScreen) {
            Text(text = "Crashlytics Screen")
        }
        Button(onClick = openFirestoreScreen) {
            Text(text = "Firestore Screen")
        }
        Button(onClick = openNotificationScreen) {
            Text(text = "Notification Screen")
        }
    }
}