package com.bimboilya.common.navigation.voyager

import android.content.Context
import android.content.Intent
import cafe.adriel.voyager.core.screen.Screen

sealed interface Destination

interface ActivityDestination : Destination {
    fun createIntent(context: Context): Intent
}

interface ScreenDestination : Destination {
    fun createScreen(): Screen
}
