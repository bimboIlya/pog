package com.bimboilya.authist.presentation

import androidx.compose.runtime.Stable
import com.bimboilya.authist.domain.entity.User

@Stable
data class AuthState(
    val isLoading: Boolean,
    val user: User?,
) {
    companion object {
        val Initial: AuthState
            get() = AuthState(
                isLoading = false,
                user = null,
            )
    }
}
