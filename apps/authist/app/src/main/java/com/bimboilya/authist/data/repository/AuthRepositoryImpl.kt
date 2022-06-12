package com.bimboilya.authist.data.repository

import com.bimboilya.authist.data.authenticator.SocialNetworkAuthenticator
import com.bimboilya.authist.data.mapper.AuthErrorMapper
import com.bimboilya.authist.data.mapper.SocialAccountToUserMapper
import com.bimboilya.authist.domain.entity.SocialNetwork
import com.bimboilya.authist.domain.entity.User
import com.bimboilya.authist.domain.repository.AuthRepository
import com.bimboilya.authist.util.mapError
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val socialNetworkAuthenticator: SocialNetworkAuthenticator,
    private val socialAccountToUserMapper: SocialAccountToUserMapper,
    private val authErrorMapper: AuthErrorMapper,
) : AuthRepository {

    override suspend fun signIn(socialNetwork: SocialNetwork): User =
        runCatching { socialNetworkAuthenticator.signIn(socialNetwork) }
            .map(socialAccountToUserMapper::map)
            .mapError(authErrorMapper::map)
            .getOrThrow()
}
