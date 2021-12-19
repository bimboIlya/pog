package com.bimboilya.navsample.common.auth

import androidx.compose.runtime.Composable
import com.bimboilya.navsample.common.GenericScreen

@Composable
fun AuthScreen(onAuthorize: () -> Unit) {
    GenericScreen(
        title = "Authorization",
        genericButtonText = "Authorize",
        genericAction = onAuthorize,
    )
}