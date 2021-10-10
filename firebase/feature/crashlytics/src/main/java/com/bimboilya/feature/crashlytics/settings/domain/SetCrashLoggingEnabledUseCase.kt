package com.bimboilya.feature.crashlytics.settings.domain

import com.bimboilya.feature.crashlytics.settings.data.CrashLoggingRepository
import javax.inject.Inject

class SetCrashLoggingEnabledUseCase @Inject constructor(
    private val repository: CrashLoggingRepository
) {

    suspend operator fun invoke(isEnabled: Boolean) {
        repository.setEnabled(isEnabled)
    }
}