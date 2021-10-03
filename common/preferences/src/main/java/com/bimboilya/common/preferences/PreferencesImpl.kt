package com.bimboilya.common.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences as DSPreferences

class PreferencesImpl constructor(
    name: String,
    context: Context,
    scope: CoroutineScope,
) : Preferences {

    private val dataStore: DataStore<DSPreferences> = PreferenceDataStoreFactory.create(scope = scope) {
        context.preferencesDataStoreFile(name)
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.put(booleanPreferencesKey(key), value)
    }

    override suspend fun putInt(key: String, value: Int) {
        dataStore.put(intPreferencesKey(key), value)
    }

    override suspend fun putLong(key: String, value: Long) {
        dataStore.put(longPreferencesKey(key), value)
    }

    override suspend fun putDouble(key: String, value: Double) {
        dataStore.put(doublePreferencesKey(key), value)
    }

    override suspend fun putString(key: String, value: String) {
        dataStore.put(stringPreferencesKey(key), value)
    }

    override suspend fun getBooleanOrNull(key: String) = dataStore.getOrNull(booleanPreferencesKey(key))

    override suspend fun getIntOrNull(key: String): Int? = dataStore.getOrNull(intPreferencesKey(key))

    override suspend fun getLongOrNull(key: String): Long? = dataStore.getOrNull(longPreferencesKey(key))

    override suspend fun getDoubleOrNull(key: String): Double? = dataStore.getOrNull(doublePreferencesKey(key))

    override suspend fun getStringOrNull(key: String): String? = dataStore.getOrNull(stringPreferencesKey(key))

    override fun observeBoolean(key: String): Flow<Boolean?> = dataStore.observe(booleanPreferencesKey(key))

    override fun observeInt(key: String): Flow<Int?> = dataStore.observe(intPreferencesKey(key))

    override fun observeLong(key: String): Flow<Long?> = dataStore.observe(longPreferencesKey(key))

    override fun observeDouble(key: String): Flow<Double?> = dataStore.observe(doublePreferencesKey(key))

    override fun observeString(key: String): Flow<String?> = dataStore.observe(stringPreferencesKey(key))

    private suspend fun <T> DataStore<DSPreferences>.put(key: DSPreferences.Key<T>, value: T) {
        this.edit { prefs ->
            prefs[key] = value
        }
    }

    private suspend fun <T> DataStore<DSPreferences>.getOrNull(key: DSPreferences.Key<T>): T? =
        this.data.map { preferences ->
            preferences[key]
        }.firstOrNull()

    private fun <T> DataStore<DSPreferences>.observe(key: DSPreferences.Key<T>): Flow<T?> =
        this.data.map { prefs ->
            prefs[key]
        }
}