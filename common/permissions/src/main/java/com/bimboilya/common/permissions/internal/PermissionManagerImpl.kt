package com.bimboilya.common.permissions.internal

import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.bimboilya.common.navigation.launcher.ActivityProvider
import com.bimboilya.common.permissions.MultipleRequestResult
import com.bimboilya.common.permissions.Permission
import com.bimboilya.common.permissions.PermissionManager
import com.bimboilya.common.permissions.PermissionState
import com.bimboilya.common.permissions.PermissionState.Denied
import com.bimboilya.common.permissions.PermissionState.Granted
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PermissionManagerImpl @Inject constructor(
    private val activityLauncher: ActivityLauncher,
    private val activityProvider: ActivityProvider,
) : PermissionManager {

    private val rationalePrefs = RationalePreferences(activityProvider.appContext)

    override fun getPermissionState(permission: Permission): PermissionState =
        if (permission.isGranted()) permission.grantedState else permission.deniedState

    override fun observePermissionState(permission: Permission): Flow<PermissionState> =
        activityProvider.observeActivity()
            .flatMapLatest { activity ->
                callbackFlow {
                    val observer = object : LifecycleEventObserver {
                        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                            when (event) {
                                ON_RESUME -> trySend(getPermissionState(permission))
                                ON_DESTROY -> activity.lifecycle.removeObserver(this)
                                else -> Unit
                            }
                        }
                    }

                    activity.lifecycle.addObserver(observer)

                    awaitClose { activity.lifecycle.removeObserver(observer) }
                }.onStart { emit(getPermissionState(permission)) }
            }

    override suspend fun requestPermission(permission: Permission): PermissionState {
        val isGranted = activityLauncher.launchAndAwaitResult(PermissionContract(), permission)

        return if (isGranted) permission.grantedState else permission.deniedState
    }

    override suspend fun requestPermissions(vararg permissions: Permission): MultipleRequestResult {
        require(permissions.isNotEmpty()) { "Requested permissions cannot be empty" }

        return activityLauncher.launchAndAwaitResult(MultiplePermissionsContract(), permissions.toList())
            .let { result ->
                MultipleRequestResult(
                    granted = result.granted.map { permission -> permission.grantedState },
                    denied = result.denied.map { permission -> permission.deniedState }
                )
            }
    }

    private val Permission.grantedState: Granted
        get() = Granted(this)

    private val Permission.deniedState: Denied
        get() = Denied(
            permission = this,
            shouldShowRationale = shouldShowRationale(),
            isDeniedPermanently = isPermanentlyDenied(),
        )

    private fun Permission.isPermanentlyDenied(): Boolean =
        !shouldShowRationale() && rationalePrefs.wasRationaleRequired(this)

    private fun Permission.isGranted(): Boolean =
        permissions.all { ContextCompat.checkSelfPermission(activityProvider.appContext, it) == PERMISSION_GRANTED }

    private fun Permission.shouldShowRationale(): Boolean {
        val activity = activityProvider.activity ?: return false
        return permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }
            .also { shouldShow -> if (shouldShow) rationalePrefs.saveRationaleRequired(this) }
    }
}
