package com.bimboilya.yacr.feature.authorization.domain.interactors

import timber.log.Timber
import javax.inject.Inject

class FetchAccessTokenUseCase @Inject constructor(

) {

    suspend operator fun invoke(code: String) {
        Timber.d("ACCESS_LOLE $code")
    }
}