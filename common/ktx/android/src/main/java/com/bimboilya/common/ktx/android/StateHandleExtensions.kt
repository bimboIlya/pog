package com.bimboilya.common.ktx.android

import androidx.lifecycle.SavedStateHandle
import com.bimboilya.common.ktx.jvm.findEnum
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun <T : Any> SavedStateHandle.getOrThrow(key: String): T =
    this.get<T>(key) ?: throw IllegalStateException("No argument with key \"$key\"")

fun <T : Any> SavedStateHandle.getOrDefault(key: String, default: T): T =
    this.get<T>(key) ?: default

inline fun <reified E : Enum<E>> SavedStateHandle.getEnumOrNull(key: String): E? =
    get<String>(key)?.findEnum<E>()

inline fun <reified E : Enum<E>> SavedStateHandle.getEnumOrThrow(key: String): E =
    getEnumOrNull<E>(key) ?: throw IllegalStateException("No ${E::class} enum with key \"$key\"")

inline fun <reified T> SavedStateHandle.getObjectOrNull(key: String, serializer: Json = Json): T? {
    val jsonString = get<String>(key) ?: return null
    return serializer.decodeFromString<T>(jsonString)
}

inline fun <reified T> SavedStateHandle.getObjectOrThrow(key: String, serializer: Json = Json): T =
    getObjectOrNull<T>(key, serializer) ?: throw IllegalStateException("No argument with key \"$key\"")
