package com.bimboilya.firebase.feature.notification

import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.common.navigation.voyager.ScreenDestination
import com.bimboilya.firebase.feature.notification.ui.NotificationScreen

class NotificationDestination : ScreenDestination {
    override fun createScreen(): Screen =
        NotificationScreen()
}