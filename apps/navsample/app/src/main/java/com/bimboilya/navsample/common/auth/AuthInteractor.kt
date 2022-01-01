package com.bimboilya.navsample.common.auth

import com.bimboilya.common.preferences.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val preferences: Preferences,
) {

    private companion object {

        const val IS_AUTHORIZED_KEY = "IS_AUTHORIZED_KEY"
    }

    suspend fun isAuthorized(): Boolean =
        preferences.getBooleanOrDefault(IS_AUTHORIZED_KEY, default = false)

    suspend fun setAuthorized(authorized: Boolean = true) {
        preferences.saveBoolean(IS_AUTHORIZED_KEY, authorized)
    }

    fun observeAuthorized(): Flow<Boolean> =
        preferences.observeBoolean(IS_AUTHORIZED_KEY, default = false)
}