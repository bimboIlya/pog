package com.bimboilya.authist.data.authenticator

import androidx.activity.result.ActivityResultLauncher
import com.bimboilya.authist.ActivityProvider
import com.bimboilya.authist.data.SocialAccount
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult.Failed
import com.vk.api.sdk.auth.VKAuthenticationResult.Success
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class VkAuthenticator @Inject constructor(
    private val activityProvider: ActivityProvider,
) {

    suspend fun signIn(): SocialAccount.Vk {
        var isAuthLaunched = false
        var launcher: ActivityResultLauncher<Collection<VKScope>>? = null

        return try {
            activityProvider.activity
                .mapLatest { activity ->
                    suspendCancellableCoroutine<VKAccessToken> { continuation ->
                        launcher = activity.activityResultRegistry.register(ResultKey, VK.getVKAuthActivityResultContract()) { result ->
                            isAuthLaunched = true

                            when (result) {
                                is Success -> continuation.resume(result.token)
                                is Failed -> continuation.resumeWithException(result.exception)
                            }
                        }

                        if (!isAuthLaunched) {
                            isAuthLaunched = true
                            launcher?.launch(setOf(VKScope.EMAIL, VKScope.OFFLINE))
                        }

                        continuation.invokeOnCancellation { launcher?.unregister() }
                    }
                }
                .first()
                .let(SocialAccount::Vk)
        } finally {
            launcher?.unregister()
        }
    }

    private companion object {
        const val ResultKey = "vk_result_key"
    }
}
