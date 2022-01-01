package com.bimboilya.yacr.feature.authorization.ui

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bimboilya.yacr.feature.authorization.AuthConfig

class AuthorizationWebViewClient(private val redirectCallback: (String) -> Unit) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
       val url = request?.url?.toString() ?: ""

        return when {
            url.startsWith(AuthConfig.REDIRECT_URL, ignoreCase = true) -> {
                redirectCallback.invoke(url)
                true
            }

            url.startsWith(AuthConfig.BASE_URL, ignoreCase = true) -> false

            else -> true
        }
    }
}
