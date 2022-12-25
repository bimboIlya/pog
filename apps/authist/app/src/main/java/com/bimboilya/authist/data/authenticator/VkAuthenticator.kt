package com.bimboilya.authist.data.authenticator

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class VkAuthenticator @Inject constructor(
    private val activityLauncher: ActivityLauncher,
) {

    suspend fun signIn(): SocialAccount.Vk =
        activityLauncher.launchAndAwaitResult(
            contract = VK.getVKAuthActivityResultContract(),
            input = setOf(VKScope.EMAIL, VKScope.OFFLINE)
        )
            .let(::mapVkAuthResult)
            .let(SocialAccount::Vk)

    private fun mapVkAuthResult(authResult: VKAuthenticationResult): VKAccessToken =
        when (authResult) {
            is VKAuthenticationResult.Success -> authResult.token
            is VKAuthenticationResult.Failed -> throw authResult.exception
        }
}
