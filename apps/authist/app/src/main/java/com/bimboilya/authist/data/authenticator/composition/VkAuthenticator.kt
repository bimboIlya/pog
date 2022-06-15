package com.bimboilya.authist.data.authenticator.composition

import com.bimboilya.authist.data.SocialAccount
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class VkAuthenticator @Inject constructor() {

    private val authenticator = Authenticator(
        activityResultContract = VK.getVKAuthActivityResultContract(),
        launcherResultKey = "vk_result_key",
        launcherInput = setOf(VKScope.EMAIL, VKScope.OFFLINE),
        onAuthorizationResult = ::mapVkAuthResult,
        transformResult = SocialAccount::Vk,
    )

    private fun mapVkAuthResult(activityResult: VKAuthenticationResult): Result<VKAccessToken> =
        when (activityResult) {
            is VKAuthenticationResult.Success -> Result.success(activityResult.token)
            is VKAuthenticationResult.Failed -> Result.failure(activityResult.exception)
        }

    suspend fun signIn(): SocialAccount.Vk =
        authenticator.signIn()
}
