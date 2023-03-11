package com.bimboilya.authist.data.authenticator

import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bimboilya.authist.R
import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
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

    suspend fun signIn(): SocialAccount.Google {
        signOut()

        return ActivityLauncher.launchAndAwaitResult(StartActivityForResult(), signInClient.signInIntent)
            .data
            .let(::requireNotNull)
            .let(GoogleSignIn::getSignedInAccountFromIntent)
            .run { getResult(ApiException::class.java) }
            .let(SocialAccount::Google)
    }

    private suspend fun signOut() {
        if (GoogleSignIn.getLastSignedInAccount(context) != null) {
            signInClient.revokeAccess().await()
            signInClient.signOut().await()
        }
    }
}
