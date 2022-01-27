package com.bimboilya.common.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface Preferences {

    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun saveInt(key: String, value: Int)
    suspend fun saveLong(key: String, value: Long)
    suspend fun saveDouble(key: String, value: Double)
    suspend fun saveString(key: String, value: String)
    suspend fun <T : Any> saveObject(key: String, value: T, serializer: KSerializer<T>)

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

    suspend fun <T : Any> getObjectOrNull(key: String, serializer: KSerializer<T>): T?
    suspend fun <T : Any> getObjectOrDefault(key: String, serializer: KSerializer<T>, default: T): T

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

    fun <T : Any> observeObject(key: String, serializer: KSerializer<T>): Flow<T?>
    fun <T : Any> observeObject(key: String, serializer: KSerializer<T>, default: T): Flow<T>
}
