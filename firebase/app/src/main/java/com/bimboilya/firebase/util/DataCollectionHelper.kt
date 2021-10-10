package com.bimboilya.firebase.util

import com.bimboilya.common.qualifiers.AppScope
import com.bimboilya.feature.crashlytics.settings.domain.ObserveCrashLoggingEnabledUseCase
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataCollectionHelper @Inject constructor(
    private val observeCrashLoggingEnabled: ObserveCrashLoggingEnabledUseCase,
    @AppScope scope: CoroutineScope
) {

    init {
        scope.launch {
            observeCrashLoggingEnabled()
                .collect(Firebase.crashlytics::setCrashlyticsCollectionEnabled)
        }
    }
}