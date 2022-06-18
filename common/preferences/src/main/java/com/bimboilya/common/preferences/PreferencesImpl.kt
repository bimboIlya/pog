package com.bimboilya.common.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class PreferencesImpl(
    name: String,
    context: Context,
    private val json: Json,
) : Preferences {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private val onSharedPreferenceChangeListener = SharedPreferenceChangeListener()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun saveLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun saveFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    override fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun <T : Any> saveObject(key: String, value: T, serializer: KSerializer<T>) {
        val jsonString = json.encodeToString(serializer, value)
        saveString(key, jsonString)
    }

    override fun getBooleanOrNull(key: String): Boolean? =
        getIfContains(key) { getBoolean(key, false) }

    override fun getBooleanOrDefault(key: String, default: Boolean): Boolean =
        sharedPreferences.getBoolean(key, default)

    override fun getIntOrNull(key: String): Int? =
        getIfContains(key) { getInt(key, 0) }

    override fun getIntOrDefault(key: String, default: Int): Int =
        sharedPreferences.getInt(key, default)

    override fun getLongOrNull(key: String): Long? =
        getIfContains(key) { getLong(key, 0) }

    override fun getLongOrDefault(key: String, default: Long): Long =
        sharedPreferences.getLong(key, default)

    override fun getFloatOrNull(key: String): Float? =
        getIfContains(key) { getFloat(key, 0F) }

    override fun getFloatOrDefault(key: String, default: Float): Float =
        sharedPreferences.getFloat(key, default)

    override fun getStringOrNull(key: String): String? =
        sharedPreferences.getString(key, null)

    override fun getStringOrDefault(key: String, default: String): String =
        sharedPreferences.getString(key, default).let(::requireNotNull)

    override fun <T : Any> getObjectOrNull(key: String, serializer: KSerializer<T>): T? {
        val jsonString = sharedPreferences.getString(key, null) ?: return null
        return json.decodeFromString(serializer, jsonString)
    }

    override fun <T : Any> getObjectOrDefault(key: String, serializer: KSerializer<T>, default: T): T {
        val jsonString = sharedPreferences.getString(key, null) ?: return default
        return json.decodeFromString(serializer, jsonString)
    }

    override fun observeBoolean(key: String): Flow<Boolean?> =
        onSharedPreferenceChangeListener.observeByKey(key) { getBooleanOrNull(key) }

    override fun observeBoolean(key: String, default: Boolean): Flow<Boolean> =
        onSharedPreferenceChangeListener.observeByKey(key) { getBooleanOrDefault(key, default) }

    override fun observeInt(key: String): Flow<Int?> =
        onSharedPreferenceChangeListener.observeByKey(key) { getIntOrNull(key) }

    override fun observeInt(key: String, default: Int): Flow<Int> =
        onSharedPreferenceChangeListener.observeByKey(key) { getIntOrDefault(key, default) }

    override fun observeLong(key: String): Flow<Long?> =
        onSharedPreferenceChangeListener.observeByKey(key) { getLongOrNull(key) }

    override fun observeLong(key: String, default: Long): Flow<Long> =
        onSharedPreferenceChangeListener.observeByKey(key) { getLongOrDefault(key, default) }

    override fun observeFloat(key: String): Flow<Float?> =
        onSharedPreferenceChangeListener.observeByKey(key) { getFloatOrNull(key) }

    override fun observeFloat(key: String, default: Float): Flow<Float> =
        onSharedPreferenceChangeListener.observeByKey(key) { getFloatOrDefault(key, default) }

    override fun observeString(key: String): Flow<String?> =
        onSharedPreferenceChangeListener.observeByKey(key) { getStringOrNull(key) }

    override fun observeString(key: String, default: String): Flow<String> =
        onSharedPreferenceChangeListener.observeByKey(key) { getStringOrDefault(key, default) }

    override fun <T : Any> observeObject(key: String, serializer: KSerializer<T>): Flow<T?> =
        onSharedPreferenceChangeListener.observeByKey(key) { getObjectOrNull(key, serializer) }

    override fun <T : Any> observeObject(key: String, serializer: KSerializer<T>, default: T): Flow<T> =
        onSharedPreferenceChangeListener.observeByKey(key) { getObjectOrDefault(key, serializer, default) }

    private fun <T> getIfContains(key: String, getter: SharedPreferences.() -> T): T? =
        if (sharedPreferences.contains(key)) getter(sharedPreferences) else null
}
