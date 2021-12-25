package com.bimboilya.navsample.common.navigation

import android.content.Context
import android.content.Intent

interface Destination

interface ActivityDestination : Destination {

    fun createIntent(context: Context): Intent
}

