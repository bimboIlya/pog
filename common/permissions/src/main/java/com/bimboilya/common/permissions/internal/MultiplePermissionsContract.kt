package com.bimboilya.common.permissions.internal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat.checkSelfPermission
import com.bimboilya.common.permissions.Permission
import com.bimboilya.common.permissions.internal.MultiplePermissionsContract.Result
import kotlin.properties.Delegates.notNull

internal class MultiplePermissionsContract : ActivityResultContract<List<Permission>, Result>() {

    private var requestedPermissions: List<Permission> by notNull()

    override fun createIntent(context: Context, input: List<Permission>): Intent =
        requestPermissionsIntent(input.flatMap(Permission::permissions))
            .also { requestedPermissions = input }

    override fun parseResult(resultCode: Int, intent: Intent?): Result {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return Result(granted = emptyList(), denied = requestedPermissions)
        }

        val resultMap = intent.getPermissionsToGrantResultsMap()

        val (granted, denied) = requestedPermissions.partition { permission ->
            permission.permissions.all { resultMap.getOrDefault(it, defaultValue = false) }
        }

        return Result(granted, denied)
    }

    override fun getSynchronousResult(context: Context, input: List<Permission>): SynchronousResult<Result>? {
        val allGranted = input.flatMap(Permission::permissions).all { checkSelfPermission(context, it) == PERMISSION_GRANTED }
        return if (allGranted) SynchronousResult(Result(granted = input, denied = emptyList())) else null
    }

    data class Result(
        val granted: List<Permission>,
        val denied: List<Permission>,
    )
}
