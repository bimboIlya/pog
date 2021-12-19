package com.bimboilya.common.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface Preferences {

    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun saveInt(key: String, value: Int)
    suspend fun saveLong(key: String, value: Long)
    suspend fun saveDouble(key: String, value: Double)
    suspend fun saveString(key: String, value: String)

    suspend fun getBooleanOrNull(key: String): Boolean?
    suspend fun getBooleanOrDefault(key: String, default: Boolean): Boolean

    suspend fun getIntOrNull(key: String): Int?
    suspend fun getIntOrDefault(key: String, default: Int): Int

    suspend fun getLongOrNull(key: String): Long?
    suspend fun getLongOrDefault(key: String, default: Long): Long

    suspend fun getDoubleOrNull(key: String): Double?
    suspend fun getDoubleOrDefault(key: String, default: Double): Double

    suspend fun getStringOrNull(key: String): String?
    suspend fun getStringOrDefault(key: String, default: String): String

    fun observeBoolean(key: String): Flow<Boolean?>
    fun observeBoolean(key: String, default: Boolean): Flow<Boolean>

    fun observeInt(key: String): Flow<Int?>
    fun observeInt(key: String, default: Int): Flow<Int>

    fun observeLong(key: String): Flow<Long?>
    fun observeLong(key: String, default: Long): Flow<Long>

    fun observeDouble(key: String): Flow<Double?>
    fun observeDouble(key: String, default: Double): Flow<Double>

    fun observeString(key: String): Flow<String?>
    fun observeString(key: String, default: String): Flow<String>

}

suspend inline fun <reified T : Any> Preferences.saveObject(key: String, value: T, serializer: Json = Json) {
    val jsonString = serializer.encodeToString(value)
    saveString(key, jsonString)
}

suspend inline fun <reified T : Any> Preferences.getObjectOrNull(key: String, serializer: Json = Json): T? {
    val jsonString = getStringOrNull(key) ?: return null
    return serializer.decodeFromString(jsonString)
}

suspend inline fun <reified T : Any> Preferences.getObjectOrDefault(key: String, default: T, serializer: Json = Json): T =
    getObjectOrNull(key, serializer) ?: default

inline fun <reified T : Any> Preferences.observeObject(key: String, serializer: Json = Json): Flow<T?> =
    observeString(key).map { jsonString ->
        if (jsonString == null) return@map null
        serializer.decodeFromString<T>(jsonString)
    }

inline fun <reified T : Any> Preferences.observeObject(key: String, default: T, serializer: Json = Json): Flow<T> =
    observeObject<T>(key, serializer).map { it ?: default }
