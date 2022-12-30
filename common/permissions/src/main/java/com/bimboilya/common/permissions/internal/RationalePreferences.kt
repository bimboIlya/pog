package com.bimboilya.common.permissions.internal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.bimboilya.common.permissions.Permission

// quite buggy with location permissions
// quite buggy overall
// assumes too much of system behaviour
// when integrating in already working application isPermanentlyDenied might be false negative
// because rationale prefs might be empty but permission could be perma denied by the time of integration
internal class RationalePreferences(context: Context) {

    private val prefs = context.getSharedPreferences("rationale_preferences", MODE_PRIVATE)

    fun wasRationaleRequired(permission: Permission): Boolean =
        prefs.getBoolean(permission.key, false)

    fun saveRationaleRequired(permission: Permission) {
        prefs.edit().putBoolean(permission.key, true).apply()
    }

    private val Permission.key: String
        get() {
            val name = this::class.simpleName ?: error("Cannot get simple name for $this")
            return "rationale_$name"
        }
}
