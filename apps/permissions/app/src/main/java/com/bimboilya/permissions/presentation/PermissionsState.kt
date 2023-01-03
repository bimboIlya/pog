package com.bimboilya.permissions.presentation

import androidx.compose.runtime.Stable
import com.bimboilya.common.permissions.Permission
import com.bimboilya.common.permissions.PermissionState

@Stable
data class PermissionsState(
    val permissionStates: List<PermissionState>,
    val settingsDialogState: SettingsDialogState,
)

sealed interface SettingsDialogState {
    object Hidden : SettingsDialogState
    @Stable data class ForSinglePermission(val permission: Permission) : SettingsDialogState
    @Stable data class ForMultiplePermissions(val permissions: List<Permission>) : SettingsDialogState
}
