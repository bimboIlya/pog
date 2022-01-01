package com.bimboilya.firebase.util

import com.bimboilya.feature.crashlytics.settings.domain.ObserveCrashLoggingEnabledUseCase
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataCollectionHelper @Inject constructor(
    observeCrashLoggingEnabled: ObserveCrashLoggingEnabledUseCase,
    scope: CoroutineScope
) {

    init {
        scope.launch {
            observeCrashLoggingEnabled()
                .collect(Firebase.crashlytics::setCrashlyticsCollectionEnabled)
        }
    }
}