package com.bimboilya.common.permissions.internal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat.checkSelfPermission
import com.bimboilya.common.permissions.Permission
import kotlin.properties.Delegates.notNull

internal class PermissionContract : ActivityResultContract<Permission, Boolean>() {

    private var requestedPermission: Permission by notNull()

    override fun createIntent(context: Context, input: Permission): Intent =
        requestPermissionsIntent(input.permissions)
            .also { requestedPermission = input }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (resultCode != Activity.RESULT_OK || intent == null) return false

        val resultMap = intent.getPermissionsToGrantResultsMap()
        return requestedPermission.permissions.all { resultMap.getOrDefault(it, defaultValue = false) }
    }

    override fun getSynchronousResult(context: Context, input: Permission): SynchronousResult<Boolean>? {
        val isGranted = input.permissions.all { checkSelfPermission(context, it) == PERMISSION_GRANTED }
        return if (isGranted) SynchronousResult(true) else null
    }
}
