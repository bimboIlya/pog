package com.bimboilya.authist.data.authenticator.composition

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bimboilya.authist.R
import com.bimboilya.authist.data.SocialAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val signInClient: GoogleSignInClient = createSignInClient()

    private val authenticator = Authenticator(
        activityResultContract = StartActivityForResult(),
        launcherResultKey = "google_result_key",
        launcherInput = signInClient.signInIntent,
        beforeAuthorization = ::signOut,
        onAuthorizationResult = ::getGoogleAccountFromIntent,
        transformResult = SocialAccount::Google,
    )

    private fun createSignInClient(): GoogleSignInClient {
        val clientId = context.getString(R.string.google_cliend_id)
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestServerAuthCode(clientId)
            .requestIdToken(clientId)
            .build()

        return GoogleSignIn.getClient(context, signInOptions)
    }

    private fun getGoogleAccountFromIntent(activityResult: ActivityResult): Result<GoogleSignInAccount> =
        runCatching {
            activityResult.data
                .let(::requireNotNull)
                .let(GoogleSignIn::getSignedInAccountFromIntent)
                .run { getResult(ApiException::class.java) }
        }

    private suspend fun signOut() {
        if (GoogleSignIn.getLastSignedInAccount(context) != null) {
            signInClient.revokeAccess().await()
            signInClient.signOut().await()
        }
    }

    suspend fun signIn(): SocialAccount.Google =
        authenticator.signIn()
}
