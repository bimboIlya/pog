package com.bimboilya.authist.domain.usecase

import com.bimboilya.authist.domain.entity.SocialNetwork
import com.bimboilya.authist.domain.entity.User
import com.bimboilya.authist.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(socialNetwork: SocialNetwork): User =
        authRepository.signIn(socialNetwork)
}
