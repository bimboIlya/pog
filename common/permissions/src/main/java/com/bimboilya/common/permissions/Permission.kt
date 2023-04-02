package com.bimboilya.common.permissions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_ADVERTISE
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.Manifest.permission.CAMERA
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.os.Build
import androidx.annotation.RequiresApi

// it's better to map permission object to platform permissions somewhere else and to remove permissions property
sealed class Permission {
    abstract val permissions: List<String>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object Notification : Permission() {
        override val permissions = listOf(POST_NOTIFICATIONS)
    }

    sealed interface Location {

        object Coarse : Location, Permission() {
            override val permissions: List<String>
                get() = listOf(ACCESS_COARSE_LOCATION)
        }

        object Fine : Location, Permission() {
            override val permissions: List<String>
                get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
                else
                    listOf(ACCESS_FINE_LOCATION)
        }
    }

    object ReadContacts : Permission() {
        override val permissions: List<String>
            get() = listOf(READ_CONTACTS)
    }

    object Camera : Permission() {
        override val permissions: List<String>
            get() = listOf(CAMERA)
    }

    sealed interface Bluetooth {

        @RequiresApi(Build.VERSION_CODES.S)
        object Scan : Bluetooth, Permission() {
            override val permissions: List<String>
                get() = listOf(BLUETOOTH_SCAN)
        }

        @RequiresApi(Build.VERSION_CODES.S)
        object Advertise : Bluetooth, Permission() {
            override val permissions: List<String>
                get() = listOf(BLUETOOTH_ADVERTISE)
        }

        @RequiresApi(Build.VERSION_CODES.S)
        object Connect : Bluetooth, Permission() {
            override val permissions: List<String>
                get() = listOf(BLUETOOTH_CONNECT)
        }
    }
}
