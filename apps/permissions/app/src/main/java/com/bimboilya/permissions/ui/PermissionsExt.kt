package com.bimboilya.permissions.ui

import com.bimboilya.common.permissions.Permission
import com.bimboilya.permissions.presentation.SettingsDialogState
import com.bimboilya.permissions.presentation.SettingsDialogState.ForMultiplePermissions
import com.bimboilya.permissions.presentation.SettingsDialogState.ForSinglePermission
import com.bimboilya.permissions.presentation.SettingsDialogState.Hidden

val Permission.name: String
    get() = permissions.last().substringAfterLast(".")

val SettingsDialogState.rationale: String
    get() {
        val permission = when (this) {
            is Hidden -> ""
            is ForSinglePermission -> permission.name
            is ForMultiplePermissions -> permissions.joinToString { it.name }
        }

        return "bro blease, we really need $permission to work properly bruv"
    }
