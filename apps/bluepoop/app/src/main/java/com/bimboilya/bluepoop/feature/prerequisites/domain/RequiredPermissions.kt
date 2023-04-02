package com.bimboilya.bluepoop.feature.prerequisites.domain

import android.os.Build
import com.bimboilya.common.permissions.Permission
import com.bimboilya.common.permissions.PermissionManager
import com.bimboilya.common.permissions.PermissionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


val RequiredBluetoothPermissions: List<Permission>
    get() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> Api31Permissions
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Api30Permissions
        else -> Api28Permissions

    }

private val Api31Permissions: List<Permission> = listOf(
    Permission.Bluetooth.Scan,
    Permission.Bluetooth.Connect,
    Permission.Bluetooth.Advertise,
)

private val Api30Permissions: List<Permission> = listOf(
    Permission.Location.Coarse,
    Permission.Location.Fine,
)

private val Api28Permissions: List<Permission> = listOf(
    Permission.Location.Coarse,
)

fun getRequiredBluetoothPermissionsStates(): List<PermissionState> =
    RequiredBluetoothPermissions.map(PermissionManager::getPermissionState)

fun observeRequiredBluetoothPermissionsStates(): Flow<List<PermissionState>> {
    val flows = RequiredBluetoothPermissions.map { permission ->
        PermissionManager.observePermissionState(permission)
    }

    return combine(flows) { it.toList() }
}


