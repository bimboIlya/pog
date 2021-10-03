package com.bimboilya.common.preferences

import kotlinx.coroutines.flow.Flow

interface Preferences {

    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun putInt(key: String, value: Int)
    suspend fun putLong(key: String, value: Long)
    suspend fun putDouble(key: String, value: Double)
    suspend fun putString(key: String, value: String)

    suspend fun getBooleanOrNull(key: String): Boolean?
    suspend fun getIntOrNull(key: String): Int?
    suspend fun getLongOrNull(key: String): Long?
    suspend fun getDoubleOrNull(key: String): Double?
    suspend fun getStringOrNull(key: String): String?

    fun observeBoolean(key: String): Flow<Boolean?>
    fun observeInt(key: String): Flow<Int?>
    fun observeLong(key: String): Flow<Long?>
    fun observeDouble(key: String): Flow<Double?>
    fun observeString(key: String): Flow<String?>
}