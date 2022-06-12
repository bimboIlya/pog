package com.bimboilya.authist.data.authenticator

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bimboilya.authist.ActivityProvider
import com.bimboilya.authist.R
import com.bimboilya.authist.data.SocialAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthenticator @Inject constructor(
    private val activityProvider: ActivityProvider,
) {

    suspend fun signIn(): SocialAccount.Google {
        /** флаг нужен для того, чтобы при пересоздании нашей активити не запускалась активити для авторизации */
        var isAuthLaunched = false

        /** нужно обязательно вызывать [ActivityResultLauncher.unregister], иначе будут непредсказуемые сайд-эффекты */
        var launcher: ActivityResultLauncher<Intent>? = null

        return try {
            activityProvider.activity
                .mapLatest { activity ->
                    val singInClient = createSignInClient(activity)

                    if (GoogleSignIn.getLastSignedInAccount(activity) != null) {
                        singInClient.revokeAccess().await()
                        singInClient.signOut().await()
                    }

                    suspendCancellableCoroutine<GoogleSignInAccount> { continuation ->
                        launcher = activity.activityResultRegistry.register(ResultKey, StartActivityForResult()) { result ->
                            isAuthLaunched = false

                            val resultIntent = requireNotNull(result.data) { "Google Login activity result intent is null" }

                            GoogleSignIn.getSignedInAccountFromIntent(resultIntent)
                                .runCatching { getResult(ApiException::class.java) }
                                .let(continuation::resumeWith)
                        }

                        if (!isAuthLaunched) {
                            isAuthLaunched = true
                            launcher?.launch(singInClient.signInIntent)
                        }

                        continuation.invokeOnCancellation { launcher?.unregister() }
                    }
                }
                .first()
                .let(SocialAccount::Google)
        } finally {
            launcher?.unregister()
        }
    }

    private fun createSignInClient(activity: ComponentActivity): GoogleSignInClient {
        val clientId = activity.getString(R.string.google_cliend_id)
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestServerAuthCode(clientId)
            .requestIdToken(clientId)
            .build()

        return GoogleSignIn.getClient(activity, signInOptions)
    }

    private companion object {
        const val ResultKey = "google_result_key"
    }
}
