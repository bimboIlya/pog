package com.bimboilya.common.permissions

import kotlinx.coroutines.flow.Flow

interface PermissionManager {

    fun getPermissionState(permission: Permission): PermissionState

    fun observePermissionState(permission: Permission): Flow<PermissionState>

    suspend fun requestPermission(permission: Permission): PermissionState

    suspend fun requestPermissions(vararg permissions: Permission): MultipleRequestResult
}

