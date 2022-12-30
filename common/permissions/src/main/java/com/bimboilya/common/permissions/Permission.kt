package com.bimboilya.common.permissions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.CAMERA
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.os.Build
import androidx.annotation.RequiresApi

sealed class Permission {
    abstract val permissions: List<String>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object Notification : Permission() {
        override val permissions = listOf(POST_NOTIFICATIONS)
    }

    object CoarseLocation : Permission() {
        override val permissions: List<String>
            get() = listOf(ACCESS_COARSE_LOCATION)
    }

    object FineLocation : Permission() {
        override val permissions: List<String>
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
            else
                listOf(ACCESS_FINE_LOCATION)
    }

    object ReadContacts : Permission() {
        override val permissions: List<String>
            get() = listOf(READ_CONTACTS)
    }

    object Camera : Permission() {
        override val permissions: List<String>
            get() = listOf(CAMERA)
    }
}
