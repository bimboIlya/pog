package com.bimboilya.common.navigation

import android.content.Intent

interface Destination

interface ActivityDestination : Destination {

    fun createIntent(): Intent
}

