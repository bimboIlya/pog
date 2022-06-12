package com.bimboilya.authist.data.mapper.google

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.authist.domain.entity.SocialNetwork.GOOGLE
import com.bimboilya.authist.domain.entity.User
import javax.inject.Inject

class GoogleUserMapper @Inject constructor() {

    fun map(googleAccount: SocialAccount.Google): User =
        with(googleAccount.value) {
            User(
                id = id.orEmpty(),
                name = displayName.orEmpty(),
                email = email.orEmpty(),
                token = idToken.orEmpty(),
                socialNetwork = GOOGLE,
            )
        }
}
