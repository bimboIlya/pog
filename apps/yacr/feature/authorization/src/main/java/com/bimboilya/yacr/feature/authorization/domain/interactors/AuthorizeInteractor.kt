package com.bimboilya.yacr.feature.authorization.domain.interactors

import com.bimboilya.yacr.feature.authorization.AuthConfig
import com.bimboilya.yacr.feature.authorization.domain.AuthException
import javax.inject.Inject

class AuthorizeInteractor @Inject constructor(
    private val fetchAccessToken: FetchAccessTokenUseCase,
) {

    suspend operator fun invoke(url: String) {
        checkUrl(url)

        val queryParamToValueMap = createQueryParamToValueMap(url)

        queryParamToValueMap.checkStateValue()

        val code = queryParamToValueMap.getCodeValueOrThrow()
        fetchAccessToken(code)
    }

    private fun checkUrl(url: String) {
        if (!url.startsWith(AuthConfig.REDIRECT_URL)) {
            throw AuthException("Received redirect url is incorrect. Url: $url")
        }
    }

    private fun createQueryParamToValueMap(url: String): Map<String, String> =
        url.removePrefix("${AuthConfig.REDIRECT_URL}/?")
            .splitToSequence('&')
            .map { it.split('=') }
            .map { it.first() to it.secondOrEmpty() }
            .toMap()

    private fun List<String>.secondOrEmpty(): String =
        if (this.size < 2) "" else this[1]

    private fun Map<String, String>.checkStateValue() {
        val state = this["state"]
        if (state != AuthConfig.STATE_STRING) {
            throw AuthException("Received state query ($state) is not equals to sent (${AuthConfig.STATE_STRING})")
        }
    }

    private fun Map<String, String>.getCodeValueOrThrow(): String {
        val code = this["code"]
        if (code.isNullOrBlank()) throw AuthException("No code was received")

        return code
    }
}