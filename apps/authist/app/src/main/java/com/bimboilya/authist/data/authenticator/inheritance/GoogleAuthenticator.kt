package com.bimboilya.authist.data.authenticator.inheritance

import android.content.Context
import android.content.Intent
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
    @ApplicationContext private val context: Context
) : Authenticator<GoogleSignInAccount, SocialAccount.Google, Intent, ActivityResult>() {

    private val signInClient: GoogleSignInClient = createSignInClient()

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

    override val activityResultContract = StartActivityForResult()

    override val launcherResultKey = "google_result_key"

    override val launcherInput = signInClient.signInIntent

    override suspend fun beforeAuthorization() {
        if (GoogleSignIn.getLastSignedInAccount(context) != null) {
            signInClient.revokeAccess().await()
            signInClient.signOut().await()
        }
    }

    override fun onAuthorizationResult(launcherResult: ActivityResult): Result<GoogleSignInAccount> =
        runCatching {
            launcherResult.data
                .let(::requireNotNull)
                .let(GoogleSignIn::getSignedInAccountFromIntent)
                .run { getResult(ApiException::class.java) }
        }

    override suspend fun transformResult(signInResult: GoogleSignInAccount): SocialAccount.Google =
        SocialAccount.Google(signInResult)
}
