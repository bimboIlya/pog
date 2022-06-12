package com.bimboilya.authist.data.mapper.google

import com.bimboilya.authist.domain.entity.AuthError
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class GoogleErrorMapper @Inject constructor() {

    fun map(error: ApiException): AuthError =
        when (error.statusCode) {
            GoogleSignInStatusCodes.CANCELED -> AuthError.Cancelled(error)
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> AuthError.Cancelled(error)
            else -> AuthError.Unknown(error)
        }

}
