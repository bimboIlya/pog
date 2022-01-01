package com.bimboilya.yacr.feature.authorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.common.ktx.jvm.launchCatching
import com.bimboilya.yacr.feature.authorization.domain.interactors.AuthorizeInteractor
import com.bimboilya.yacr.feature.authorization.ui.AuthorizationWebViewClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authorize: AuthorizeInteractor,
    private val router: AuthorizationRouter,
) : ViewModel() {

    val webViewClient = AuthorizationWebViewClient(::authorize)

    private fun authorize(redirectUrl: String) {
        viewModelScope.launchCatching(::handleError) {
            authorize.invoke(redirectUrl)
        }
    }

    private fun handleError(error: Throwable) {

    }
}