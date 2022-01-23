package com.bimboilya.firebase.feature.firestore.util

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun Any?.toJsonElement(): JsonElement {
    return when (this) {
        is Number -> JsonPrimitive(this)
        is Boolean -> JsonPrimitive(this)
        is String -> JsonPrimitive(this)
        is Array<*> -> this.toJsonArray()
        is Collection<*> -> this.toJsonArray()
        is Map<*, *> -> this.toJsonObject()
        is JsonElement -> this
        else -> JsonNull
    }
}

fun Array<*>.toJsonArray(): JsonArray =
    map { it.toJsonElement() }
        .let(::JsonArray)

fun Collection<*>.toJsonArray(): JsonArray =
    map { it.toJsonElement() }
        .let(::JsonArray)

/** Ignores maps with non-string keys */
fun Map<*, *>.toJsonObject(): JsonObject {
    val map = mutableMapOf<String, JsonElement>()
    this.forEach { (key, value) ->
        if (key is String) {
            map[key] = value.toJsonElement()
        }
    }
    return JsonObject(map)
}