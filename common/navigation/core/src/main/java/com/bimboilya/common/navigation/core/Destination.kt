package com.bimboilya.common.navigation.core

import android.content.Intent

interface Destination

interface ActivityDestination : Destination {

    fun createIntent(): Intent
}

