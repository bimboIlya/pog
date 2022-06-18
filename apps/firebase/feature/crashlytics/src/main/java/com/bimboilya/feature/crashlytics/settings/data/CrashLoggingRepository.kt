package com.bimboilya.feature.crashlytics.settings.data

import com.bimboilya.common.preferences.async.AsyncPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CrashLoggingRepository @Inject constructor(
    private val preferences: AsyncPreferences
) {

    private companion object {
        const val IS_CRASH_LOGGING_ENABLED = "IS_CRASH_LOGGING_ENABLED"
    }

    suspend fun setEnabled(isEnabled: Boolean) {
        preferences.saveBoolean(IS_CRASH_LOGGING_ENABLED, isEnabled)
    }

    fun observeEnabled(): Flow<Boolean> =
        preferences.observeBoolean(IS_CRASH_LOGGING_ENABLED, default = false)
}
