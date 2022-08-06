package com.bimboilya.authist.data.authenticator.inheritance

import com.bimboilya.authist.data.SocialAccount
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class VkAuthenticator @Inject constructor() : Authenticator<VKAccessToken, SocialAccount.Vk, Collection<VKScope>, VKAuthenticationResult>() {

    override val activityResultContract = VK.getVKAuthActivityResultContract()

    override val launcherResultKey = "vk_result_key"

    override val launcherInput = setOf(VKScope.EMAIL, VKScope.OFFLINE)

    override fun onAuthorizationResult(launcherResult: VKAuthenticationResult): Result<VKAccessToken> =
        when (launcherResult) {
            is VKAuthenticationResult.Success -> Result.success(launcherResult.token)
            is VKAuthenticationResult.Failed -> Result.failure(launcherResult.exception)
        }

    override suspend fun transformResult(signInResult: VKAccessToken): SocialAccount.Vk =
        SocialAccount.Vk(signInResult)
}
