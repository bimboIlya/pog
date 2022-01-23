package com.bimboilya.firebase.feature.firestore.util

import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

inline fun <reified T> QueryDocumentSnapshot.convertToObject(serializer: Json): T =
    serializer.decodeFromJsonElement(data.toJsonObject())
