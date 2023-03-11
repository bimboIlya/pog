package com.bimboilya.permissions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.common.permissions.Permission
import com.bimboilya.common.permissions.Permission.Camera
import com.bimboilya.common.permissions.Permission.Notification
import com.bimboilya.common.permissions.Permission.ReadContacts
import com.bimboilya.common.permissions.PermissionManager
import com.bimboilya.common.permissions.PermissionState
import com.bimboilya.common.permissions.isGranted
import com.bimboilya.permissions.presentation.SettingsDialogState.ForSinglePermission
import com.bimboilya.permissions.presentation.SettingsDialogState.Hidden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    private val router: PermissionsRouter,
) : ViewModel() {

    private val defaultState: PermissionsState
        get() = PermissionsState(
            permissionStates = getPermissionsInitialStates(),
            settingsDialogState = Hidden
        )

    private fun getPermissionsInitialStates(): List<PermissionState> =
        Permission::class.sealedSubclasses.mapNotNull { it.objectInstance }
            .map(PermissionManager::getPermissionState)

    private val _state = MutableStateFlow(defaultState)
    val state = _state.asStateFlow()

    fun requestPermission(permission: Permission) {
        viewModelScope.launch {
            val result = PermissionManager.requestPermission(permission)
            replace(result)

            if (!result.isGranted() && result.isDeniedPermanently) {
                _state.update { state -> state.copy(settingsDialogState = ForSinglePermission(permission)) }
            }
        }
    }

    private fun replace(newPermissionState: PermissionState) {
        val permissionStates = _state.value.permissionStates
        val permiState = permissionStates.first { it.permission === newPermissionState.permission }
        val indexToReplace = permissionStates.indexOf(permiState)
        val mutable = permissionStates.toMutableList()
        mutable.removeAt(indexToReplace)
        mutable.add(indexToReplace, newPermissionState)
        _state.update { state -> state.copy(permissionStates = mutable.toList()) }
    }

    fun dismissSettingsDialog() {
        _state.update { state -> state.copy(settingsDialogState = Hidden) }
    }

    fun openSettings() {
        router.openSettings()
    }

    fun requestMultiple() {
        viewModelScope.launch {
            val result = PermissionManager.requestPermissions(Notification, ReadContacts, Camera)
            val notificationState = result.getResultFor(Notification)
            val contactsState = result.getResultFor(ReadContacts)
            val cameraState = result.getResultFor(Camera)

            result.denied.forEach { it.log() }
        }
    }


    private fun PermissionState.Denied.log() {
        Timber.d("poop | permission = ${permission::class.simpleName!!} | shouldShowRationale = $shouldShowRationale | isDeniedPermanently = $isDeniedPermanently")
    }
}
