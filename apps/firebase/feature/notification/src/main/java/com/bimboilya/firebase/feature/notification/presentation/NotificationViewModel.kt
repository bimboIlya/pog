package com.bimboilya.firebase.feature.notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.common.ktx.jvm.combineState
import com.bimboilya.firebase.feature.notification.domain.NotificationInteractor
import com.bimboilya.firebase.feature.notification.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val interactor: NotificationInteractor,
) : ViewModel() {

    private val _newTokenEvent = Channel<String>(Channel.CONFLATED)
    val newTokenEvent = _newTokenEvent.receiveAsFlow()

    private val loading = MutableStateFlow(State.Initial.loading)
    private val token = MutableStateFlow(State.Initial.token)
    val state = viewModelScope.combineState(loading, token, ::State)

    fun getToken() {
        viewModelScope.launch {
            loading.value = true
            val token = interactor.getToken()
            loading.value = false
            log(token)
            _newTokenEvent.send(token)
        }
    }
}

data class State(
    val loading: Boolean,
    val token: String,
) {

    companion object {
        val Initial = State(
            loading = false,
            token = "",
        )
    }
}