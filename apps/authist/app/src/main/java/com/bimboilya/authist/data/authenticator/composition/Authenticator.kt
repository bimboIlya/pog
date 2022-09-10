package com.bimboilya.authist.data.authenticator.composition

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.bimboilya.authist.ActivityProvider
import com.bimboilya.authist.data.SocialAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class Authenticator<T, S : SocialAccount, I, O>(
    private val activityResultContract: ActivityResultContract<I, O>,
    private val launcherResultKey: String,
    private val launcherInput: I,
    private val beforeAuthorization: suspend () -> Unit = {},
    private val onAuthorizationResult: (launcherResult: O) -> Result<T>,
    private val transformResult: suspend (signInResult: T) -> S
) {

    suspend fun signIn(): S = withContext(Dispatchers.Main.immediate) {
        /** флаг нужен для того, чтобы при пересоздании нашей активити не запускалась активити для авторизации */
        var isAuthLaunched = false

        /** нужно обязательно вызывать [ActivityResultLauncher.unregister], иначе будут непредсказуемые сайд-эффекты */
        var launcher: ActivityResultLauncher<I>? = null

        beforeAuthorization()

        ActivityProvider.activity
            .mapLatest { activity ->
                suspendCancellableCoroutine { continuation ->
                    launcher = activity.activityResultRegistry.register(launcherResultKey, activityResultContract) { result ->
                        isAuthLaunched = false

                        onAuthorizationResult(result)
                            .let(continuation::resumeWith)
                    }

                    if (!isAuthLaunched) {
                        isAuthLaunched = true
                        launcher?.launch(launcherInput)
                    }

                    continuation.invokeOnCancellation { launcher?.unregister() }
                }
            }
            .map { transformResult(it) }
            .onCompletion { launcher?.unregister() }
            .first()
    }
}
