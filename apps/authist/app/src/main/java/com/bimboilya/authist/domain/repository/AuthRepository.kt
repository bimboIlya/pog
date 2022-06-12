package com.bimboilya.authist.domain.repository

import com.bimboilya.authist.domain.entity.SocialNetwork
import com.bimboilya.authist.domain.entity.User

interface AuthRepository {

    suspend fun signIn(socialNetwork: SocialNetwork): User
}
