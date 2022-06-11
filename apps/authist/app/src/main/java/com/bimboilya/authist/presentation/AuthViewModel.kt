package com.bimboilya.authist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.authist.domain.entity.SocialNetwork
import com.bimboilya.authist.domain.usecase.SignInUseCase
import com.bimboilya.common.ktx.jvm.launchCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signIn: SignInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState.Initial)
    val state = _state.asStateFlow()

    private val _event = Channel<Unit>(UNLIMITED)
    val event = _event.receiveAsFlow()

    fun signIn(socialNetwork: SocialNetwork) {
        viewModelScope.launchCatching(::onLoginError) {
            applyLoadingState(true)

            signIn.invoke(socialNetwork)

            applyLoadingState(false)
        }
    }

    private fun onLoginError(error: Throwable) {
        applyLoadingState(false)
        _event.trySend(Unit)
    }

    private fun applyLoadingState(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}
