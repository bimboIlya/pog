package com.bimboilya.feature.crashlytics.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.feature.crashlytics.settings.domain.ObserveCrashLoggingEnabledUseCase
import com.bimboilya.feature.crashlytics.settings.domain.SetCrashLoggingEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrashSettingsViewModel @Inject constructor(
    private val setCrashLoggingEnabled: SetCrashLoggingEnabledUseCase,
    observeCrashLoggingEnabled: ObserveCrashLoggingEnabledUseCase,
) : ViewModel() {

    val crashLoggingEnabled = observeCrashLoggingEnabled()

    fun setLoggingEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            setCrashLoggingEnabled(isEnabled)
        }
    }
}