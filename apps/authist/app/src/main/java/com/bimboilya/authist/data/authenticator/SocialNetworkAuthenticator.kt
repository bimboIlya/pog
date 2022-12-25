package com.bimboilya.authist.data.authenticator

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.authist.domain.entity.SocialNetwork
import javax.inject.Inject

class SocialNetworkAuthenticator @Inject constructor(
    private val googleAuthenticator: GoogleAuthenticator,
    private val vkAuthenticator: VkAuthenticator,
    private val yandexAuthenticator: YandexAuthenticator,
) {

    suspend fun signIn(socialNetwork: SocialNetwork): SocialAccount =
        when (socialNetwork) {
            SocialNetwork.GOOGLE -> googleAuthenticator.signIn()
            SocialNetwork.VK -> vkAuthenticator.signIn()
            SocialNetwork.YANDEX -> yandexAuthenticator.signIn()
        }
}
