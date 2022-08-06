package com.bimboilya.authist.data.authenticator.composition

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bimboilya.authist.BuildConfig
import com.bimboilya.authist.data.SocialAccount
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class YandexAuthenticator @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val yandexSdk = YandexAuthSdk(
        context,
        YandexAuthOptions.Builder(context)
            .apply { if (BuildConfig.DEBUG) enableLogging() }
            .build()
    )

    private val authenticator = Authenticator(
        activityResultContract = StartActivityForResult(),
        launcherResultKey = "yandex_result_key",
        launcherInput = yandexSdk.createLoginIntent(context, setOf()),
        onAuthorizationResult = ::getTokenFromResult,
        transformResult = SocialAccount::Yandex,
    )

    private fun getTokenFromResult(activityResult: ActivityResult): Result<String> =
        runCatching {
            yandexSdk.extractToken(activityResult.resultCode, activityResult.data)
                .let(::requireNotNull)
                .value
        }

    suspend fun signIn(): SocialAccount.Yandex =
        authenticator.signIn()
}
