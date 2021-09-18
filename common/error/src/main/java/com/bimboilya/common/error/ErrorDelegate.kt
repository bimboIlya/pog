package com.bimboilya.common.error

import javax.inject.Inject

interface ErrorDelegate {

    fun getStateByError(error: Throwable): ErrorState
}

class ErrorDelegateImpl @Inject constructor() : ErrorDelegate {

    override fun getStateByError(error: Throwable): ErrorState {
        return NoInternetState
    }
}