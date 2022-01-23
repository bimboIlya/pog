package com.bimboilya.firebase.feature.firestore.data

import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val first: String,
    val second: String?,
)
