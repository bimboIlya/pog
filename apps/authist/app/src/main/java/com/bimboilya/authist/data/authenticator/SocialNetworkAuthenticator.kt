package com.bimboilya.authist.data.authenticator

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.authist.data.authenticator.composition.GoogleAuthenticator
import com.bimboilya.authist.data.authenticator.composition.VkAuthenticator
import com.bimboilya.authist.domain.entity.SocialNetwork
import javax.inject.Inject

class SocialNetworkAuthenticator @Inject constructor(
    private val googleAuthenticator: GoogleAuthenticator,
    private val vkAuthenticator: VkAuthenticator,
) {

    suspend fun signIn(socialNetwork: SocialNetwork): SocialAccount =
        when (socialNetwork) {
            SocialNetwork.GOOGLE -> googleAuthenticator.signIn()
            SocialNetwork.VK -> vkAuthenticator.signIn()
        }
}
