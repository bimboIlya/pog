package com.bimboilya.common.permissions

import com.bimboilya.common.permissions.PermissionState.Denied
import com.bimboilya.common.permissions.PermissionState.Granted

class MultipleRequestResult(
    val granted: List<Granted>,
    val denied: List<Denied>,
) {

    init {
        require(granted.isNotEmpty() || denied.isNotEmpty()) {
            "Granted and denied permissions are both empty"
        }
    }

    val allGranted: Boolean
        get() = denied.isEmpty()

    fun getResultFor(permission: Permission): PermissionState =
        granted.find { it.permission == permission }
            ?: denied.find { it.permission == permission }
            ?: error("No result for permission $permission")
}
