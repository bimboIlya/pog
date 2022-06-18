package com.bimboilya.common.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface Preferences {

    fun saveBoolean(key: String, value: Boolean)
    fun saveInt(key: String, value: Int)
    fun saveLong(key: String, value: Long)
    fun saveFloat(key: String, value: Float)
    fun saveString(key: String, value: String)
    fun <T : Any> saveObject(key: String, value: T, serializer: KSerializer<T>)

    fun getBooleanOrNull(key: String): Boolean?
    fun getBooleanOrDefault(key: String, default: Boolean): Boolean

    fun getIntOrNull(key: String): Int?
    fun getIntOrDefault(key: String, default: Int): Int

    fun getLongOrNull(key: String): Long?
    fun getLongOrDefault(key: String, default: Long): Long

    fun getFloatOrNull(key: String): Float?
    fun getFloatOrDefault(key: String, default: Float): Float

    fun getStringOrNull(key: String): String?
    fun getStringOrDefault(key: String, default: String): String

    fun <T : Any> getObjectOrNull(key: String, serializer: KSerializer<T>): T?
    fun <T : Any> getObjectOrDefault(key: String, serializer: KSerializer<T>, default: T): T

    fun observeBoolean(key: String): Flow<Boolean?>
    fun observeBoolean(key: String, default: Boolean): Flow<Boolean>

    fun observeInt(key: String): Flow<Int?>
    fun observeInt(key: String, default: Int): Flow<Int>

    fun observeLong(key: String): Flow<Long?>
    fun observeLong(key: String, default: Long): Flow<Long>

    fun observeFloat(key: String): Flow<Float?>
    fun observeFloat(key: String, default: Float): Flow<Float>

    fun observeString(key: String): Flow<String?>
    fun observeString(key: String, default: String): Flow<String>

    fun <T : Any> observeObject(key: String, serializer: KSerializer<T>): Flow<T?>
    fun <T : Any> observeObject(key: String, serializer: KSerializer<T>, default: T): Flow<T>
}
