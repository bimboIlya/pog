package com.bimboilya.common.ktx.android

import androidx.lifecycle.SavedStateHandle
import com.bimboilya.common.ktx.jvm.findEnum
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun <T : Any> SavedStateHandle.getOrThrow(key: String): T =
    this.get<T>(key) ?: throw IllegalStateException("No argument with key \"$key\"")

fun <T : Any> SavedStateHandle.getOrDefault(key: String, default: T): T =
    this.get<T>(key) ?: default

inline fun <reified T : Enum<*>> SavedStateHandle.getEnumOrNull(key: String): T? =
    get<String>(key)?.findEnum()

inline fun <reified T : Enum<*>> SavedStateHandle.getEnumOrThrow(key: String): T =
    getEnumOrNull(key) ?: throw IllegalStateException("No ${T::class} enum with key \"$key\"")

inline fun <reified T> SavedStateHandle.getObjectOrNull(key: String, serializer: Json = Json): T? {
    val jsonString = get<String>(key) ?: return null
    return serializer.decodeFromString<T>(jsonString)
}

inline fun <reified T> SavedStateHandle.getObjectOrThrow(key: String, serializer: Json = Json): T =
    getObjectOrNull<T>(key, serializer) ?: throw IllegalStateException("No argument with key \"$key\"")
