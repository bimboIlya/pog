package com.bimboilya.common.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class SharedPreferenceChangeListener : SharedPreferences.OnSharedPreferenceChangeListener {

    private val keyFlow = MutableSharedFlow<String>()

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences != null && key != null) {
            keyFlow.tryEmit(key)
        }
    }

    fun <T> observeByKey(key: String, getter: () -> T): Flow<T> =
        keyFlow.filter { it == key }
            .map { getter() }
}
