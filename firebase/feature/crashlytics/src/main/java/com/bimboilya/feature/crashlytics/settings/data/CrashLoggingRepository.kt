package com.bimboilya.feature.crashlytics.settings.data

import com.bimboilya.common.preferences.Preferences
import com.bimboilya.common.qualifiers.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CrashLoggingRepository @Inject constructor(
    @AppPreferences private val preferences: Preferences
) {

    private companion object {
        const val IS_CRASH_LOGGING_ENABLED = "IS_CRASH_LOGGING_ENABLED"
    }

    suspend fun setEnabled(isEnabled: Boolean) {
        preferences.saveBoolean(IS_CRASH_LOGGING_ENABLED, isEnabled)
    }

    fun observeEnabled(): Flow<Boolean> =
        preferences.observeBoolean(IS_CRASH_LOGGING_ENABLED)
            .map { it ?: false }

}