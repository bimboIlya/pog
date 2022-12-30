package com.bimboilya.common.permissions

interface PermissionManager {

    fun getPermissionState(permission: Permission): PermissionState

    suspend fun requestPermission(permission: Permission): PermissionState

    suspend fun requestPermissions(vararg permissions: Permission): MultipleRequestResult
}

