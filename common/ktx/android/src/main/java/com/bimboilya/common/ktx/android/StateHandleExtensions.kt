package com.bimboilya.common.ktx.android

import androidx.lifecycle.SavedStateHandle

fun <T : Any> SavedStateHandle.getOrThrow(key: String): T =
    this.get<T>(key) ?: throw IllegalStateException("No argument with key \"$key\"")

fun <T : Any> SavedStateHandle.getOrDefault(key: String, default: T): T =
    this.get<T>(key) ?: default

inline fun <reified T : Enum<*>> SavedStateHandle.getEnumOrNull(key: String): T? {
    val stringifiedEnum = this.get<String>(key)
    return T::class.java.enumConstants?.find { it.name == stringifiedEnum }
}