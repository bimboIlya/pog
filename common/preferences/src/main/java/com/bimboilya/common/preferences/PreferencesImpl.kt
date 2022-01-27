package com.bimboilya.common.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import androidx.datastore.preferences.core.Preferences as DSPreferences

class PreferencesImpl constructor(
    name: String,
    context: Context,
    scope: CoroutineScope,
    private val json: Json,
) : Preferences {

    private val dataStore: DataStore<DSPreferences> = PreferenceDataStoreFactory.create(scope = scope) {
        context.preferencesDataStoreFile(name)
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.put(booleanPreferencesKey(key), value)
    }

    override suspend fun saveInt(key: String, value: Int) {
        dataStore.put(intPreferencesKey(key), value)
    }

    override suspend fun saveLong(key: String, value: Long) {
        dataStore.put(longPreferencesKey(key), value)
    }

    override suspend fun saveDouble(key: String, value: Double) {
        dataStore.put(doublePreferencesKey(key), value)
    }

    override suspend fun saveString(key: String, value: String) {
        dataStore.put(stringPreferencesKey(key), value)
    }

    override suspend fun <T : Any> saveObject(key: String, value: T, serializer: KSerializer<T>) {
        val jsonString = json.encodeToString(serializer, value)
        saveString(key, jsonString)
    }

    override suspend fun getBooleanOrNull(key: String) = dataStore.getOrNull(booleanPreferencesKey(key))

    override suspend fun getBooleanOrDefault(key: String, default: Boolean): Boolean = getBooleanOrNull(key) ?: default

    override suspend fun getIntOrNull(key: String): Int? = dataStore.getOrNull(intPreferencesKey(key))

    override suspend fun getIntOrDefault(key: String, default: Int): Int = getIntOrNull(key) ?: default

    override suspend fun getLongOrNull(key: String): Long? = dataStore.getOrNull(longPreferencesKey(key))

    override suspend fun getLongOrDefault(key: String, default: Long): Long = getLongOrNull(key) ?: default

    override suspend fun getDoubleOrNull(key: String): Double? = dataStore.getOrNull(doublePreferencesKey(key))

    override suspend fun getDoubleOrDefault(key: String, default: Double): Double = getDoubleOrNull(key) ?: default

    override suspend fun getStringOrNull(key: String): String? = dataStore.getOrNull(stringPreferencesKey(key))

    override suspend fun getStringOrDefault(key: String, default: String): String = getStringOrNull(key) ?: default

    override suspend fun <T : Any> getObjectOrNull(key: String, serializer: KSerializer<T>): T? {
        val jsonString = getStringOrNull(key) ?: return null
        return json.decodeFromString(serializer, jsonString)
    }

    override suspend fun <T : Any> getObjectOrDefault(key: String, serializer: KSerializer<T>, default: T): T =
        getObjectOrNull(key, serializer) ?: default

    override fun observeBoolean(key: String): Flow<Boolean?> = dataStore.observe(booleanPreferencesKey(key))

    override fun observeBoolean(key: String, default: Boolean): Flow<Boolean> = observeBoolean(key).orDefault(default)

    override fun observeInt(key: String): Flow<Int?> = dataStore.observe(intPreferencesKey(key))

    override fun observeInt(key: String, default: Int): Flow<Int> = observeInt(key).orDefault(default)

    override fun observeLong(key: String): Flow<Long?> = dataStore.observe(longPreferencesKey(key))

    override fun observeLong(key: String, default: Long): Flow<Long> = observeLong(key).orDefault(default)

    override fun observeDouble(key: String): Flow<Double?> = dataStore.observe(doublePreferencesKey(key))

    override fun observeDouble(key: String, default: Double): Flow<Double> = observeDouble(key).orDefault(default)

    override fun observeString(key: String): Flow<String?> = dataStore.observe(stringPreferencesKey(key))

    override fun observeString(key: String, default: String): Flow<String> = observeString(key).orDefault(default)

    override fun <T : Any> observeObject(key: String, serializer: KSerializer<T>): Flow<T?> =
        observeString(key).map { jsonString ->
            if (jsonString == null) return@map null
            json.decodeFromString(serializer, jsonString)
        }

    override fun <T : Any> observeObject(key: String, serializer: KSerializer<T>, default: T): Flow<T> =
        observeObject(key, serializer).orDefault(default)

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

    private fun <T : Any> Flow<T?>.orDefault(default: T) = this.map { it ?: default }
}