package com.bimboilya.authist.data.authenticator.inheritance

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

abstract class Authenticator<T, S : SocialAccount, I, O> {

    abstract val activityResultContract: ActivityResultContract<I, O>

    abstract val launcherResultKey: String

    abstract val launcherInput: I

    open suspend fun beforeAuthorization() = Unit

    abstract fun onAuthorizationResult(launcherResult: O): Result<T>

    abstract suspend fun transformResult(signInResult: T): S

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
