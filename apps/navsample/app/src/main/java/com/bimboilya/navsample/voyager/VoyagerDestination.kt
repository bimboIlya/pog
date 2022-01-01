package com.bimboilya.navsample.voyager

import android.content.Context
import android.content.Intent
import cafe.adriel.voyager.core.screen.Screen

sealed interface VoyagerDestination

interface ActivityDestination : VoyagerDestination {
    fun createIntent(context: Context): Intent
}

interface ScreenDestination : VoyagerDestination {
    fun createScreen(): Screen
}
