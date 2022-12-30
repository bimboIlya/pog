package com.bimboilya.common.permissions.internal

import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.Companion.ACTION_REQUEST_PERMISSIONS
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.Companion.EXTRA_PERMISSIONS
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.Companion.EXTRA_PERMISSION_GRANT_RESULTS

internal fun requestPermissionsIntent(permissions: List<String>): Intent =
    Intent(ACTION_REQUEST_PERMISSIONS).putExtra(EXTRA_PERMISSIONS, permissions.toTypedArray())

internal fun Intent.getPermissionsToGrantResultsMap(): Map<String, Boolean> {
    val permissions = getStringArrayExtra(EXTRA_PERMISSIONS) ?: return emptyMap()
    val grantResults = getIntArrayExtra(EXTRA_PERMISSION_GRANT_RESULTS) ?: return emptyMap()

    return permissions.mapIndexed { index, permission ->
        permission to (grantResults[index] == PackageManager.PERMISSION_GRANTED)
    }.toMap()
}
