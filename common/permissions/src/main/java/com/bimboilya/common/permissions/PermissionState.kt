package com.bimboilya.common.permissions

import com.bimboilya.common.permissions.PermissionState.Denied
import com.bimboilya.common.permissions.PermissionState.Granted
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class PermissionState {
    abstract val permission: Permission

    class Granted(override val permission: Permission) : PermissionState()

    class Denied(
        override val permission: Permission,
        val shouldShowRationale: Boolean,
        val isDeniedPermanently: Boolean,
    ) : PermissionState() {

        val notRequestedBefore: Boolean
            get() = !shouldShowRationale && !isDeniedPermanently
    }
}

@OptIn(ExperimentalContracts::class)
fun PermissionState.isGranted(): Boolean {
    contract {
        returns(false) implies (this@isGranted is Denied)
        returns(true) implies (this@isGranted is Granted)
    }

    return this is Granted
}

@OptIn(ExperimentalContracts::class)
fun PermissionState.isPotentiallyPermanentlyDenied(): Boolean {
    contract {
        returns(true) implies (this@isPotentiallyPermanentlyDenied is Denied)
    }

    return !isGranted() && !shouldShowRationale
}
