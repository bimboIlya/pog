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
    private val activityLauncher: ActivityLauncher,
) {

    private val yandexSdk = YandexAuthSdk(
        context,
        YandexAuthOptions.Builder(context)
            .apply { if (BuildConfig.DEBUG) enableLogging() }
            .build()
    )

    suspend fun signIn(): SocialAccount.Yandex =
        activityLauncher.launchAndAwaitResult(StartActivityForResult(), yandexSdk.createLoginIntent(context, setOf()))
            .let { result -> yandexSdk.extractToken(result.resultCode, result.data) }
            ?.value
            ?.let(SocialAccount::Yandex)
            ?: error("Yandex authorization error")
}
