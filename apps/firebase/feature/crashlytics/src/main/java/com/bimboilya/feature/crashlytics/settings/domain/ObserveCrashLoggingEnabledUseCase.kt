package com.bimboilya.feature.crashlytics.settings.domain

import com.bimboilya.feature.crashlytics.settings.data.CrashLoggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCrashLoggingEnabledUseCase @Inject constructor(
    private val repository: CrashLoggingRepository,
) {

    operator fun invoke(): Flow<Boolean> =
        repository.observeEnabled()
}