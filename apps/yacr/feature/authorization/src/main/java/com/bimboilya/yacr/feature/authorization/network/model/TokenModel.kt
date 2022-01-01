package com.bimboilya.yacr.feature.authorization.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenModel(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
    val refreshToken: String? = null,
)
