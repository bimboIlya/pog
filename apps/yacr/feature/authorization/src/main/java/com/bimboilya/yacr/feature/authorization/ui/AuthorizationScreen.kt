package com.bimboilya.yacr.feature.authorization.ui

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bimboilya.yacr.feature.authorization.AuthConfig
import com.bimboilya.yacr.feature.authorization.presentation.AuthViewModel

@Composable
fun AuthorizationScreen(viewModel: AuthViewModel) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = viewModel.webViewClient
                loadUrl(AuthConfig.fullUrl)
            }
        }
    )
}
