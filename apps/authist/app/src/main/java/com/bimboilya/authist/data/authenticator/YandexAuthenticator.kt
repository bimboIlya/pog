package com.bimboilya.authist.data.authenticator

import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bimboilya.authist.BuildConfig
import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class YandexAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val yandexSdk = YandexAuthSdk(
        context,
        YandexAuthOptions(context, loggingEnabled = BuildConfig.DEBUG)
    )

    suspend fun signIn(): SocialAccount.Yandex =
        ActivityLauncher.launchAndAwaitResult(StartActivityForResult(), yandexSdk.createLoginIntent(setOf(), setOf()))
            .let { result -> yandexSdk.extractToken(result.resultCode, result.data) }
            ?.value
            ?.let(SocialAccount::Yandex)
            ?: error("Yandex authorization error")
}
