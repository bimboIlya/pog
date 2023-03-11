package com.bimboilya.common.permissions

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.bimboilya.common.navigation.launcher.ActivityProvider
import com.bimboilya.common.permissions.internal.MultiplePermissionsContract
import com.bimboilya.common.permissions.internal.PermissionContract
import com.bimboilya.common.permissions.internal.RationalePreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

object PermissionManager {

    private val rationalePrefs = RationalePreferences(ActivityProvider.appContext)

    fun getPermissionState(permission: Permission): PermissionState =
        if (permission.isGranted()) permission.grantedState else permission.deniedState

    fun observePermissionState(permission: Permission): Flow<PermissionState> =
        ActivityProvider.observeActivity()
            .flatMapLatest { activity ->
                callbackFlow {
                    val observer = object : LifecycleEventObserver {
                        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                            when (event) {
                                Lifecycle.Event.ON_RESUME -> trySend(getPermissionState(permission))
                                Lifecycle.Event.ON_DESTROY -> activity.lifecycle.removeObserver(this)
                                else -> Unit
                            }
                        }
                    }

                    activity.lifecycle.addObserver(observer)

                    awaitClose { activity.lifecycle.removeObserver(observer) }
                }.onStart { emit(getPermissionState(permission)) }
            }

    suspend fun requestPermission(permission: Permission): PermissionState {
        val isGranted = ActivityLauncher.launchAndAwaitResult(PermissionContract(), permission)

        return if (isGranted) permission.grantedState else permission.deniedState
    }

    suspend fun requestPermissions(vararg permissions: Permission): MultipleRequestResult {
        require(permissions.isNotEmpty()) { "Requested permissions cannot be empty" }

        return ActivityLauncher.launchAndAwaitResult(MultiplePermissionsContract(), permissions.toList())
            .let { result ->
                MultipleRequestResult(
                    granted = result.granted.map { permission -> permission.grantedState },
                    denied = result.denied.map { permission -> permission.deniedState }
                )
            }
    }

    private val Permission.grantedState: PermissionState.Granted
        get() = PermissionState.Granted(this)

    private val Permission.deniedState: PermissionState.Denied
        get() = PermissionState.Denied(
            permission = this,
            shouldShowRationale = shouldShowRationale(),
            isDeniedPermanently = isPermanentlyDenied(),
        )

    private fun Permission.isPermanentlyDenied(): Boolean =
        !shouldShowRationale() && rationalePrefs.wasRationaleRequired(this)

    private fun Permission.isGranted(): Boolean =
        permissions.all { ContextCompat.checkSelfPermission(ActivityProvider.appContext, it) == PackageManager.PERMISSION_GRANTED }

    private fun Permission.shouldShowRationale(): Boolean {
        val activity = ActivityProvider.activity ?: return false
        return permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }
            .also { shouldShow -> if (shouldShow) rationalePrefs.saveRationaleRequired(this) }
    }
}

