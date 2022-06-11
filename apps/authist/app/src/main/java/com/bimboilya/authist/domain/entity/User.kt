package com.bimboilya.authist.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val token: String,
    val socialNetwork: SocialNetwork,
)
