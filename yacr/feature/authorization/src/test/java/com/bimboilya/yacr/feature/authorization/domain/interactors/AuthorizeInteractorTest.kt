package com.bimboilya.yacr.feature.authorization.domain.interactors

import com.bimboilya.yacr.feature.authorization.domain.AuthException
import io.mockk.coVerify
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(MockKExtension::class)
class AuthorizeInteractorTest {

    private val fetchAccessTokenUseCase: FetchAccessTokenUseCase = mockk(relaxUnitFun = true)
    private val authorizeInteractor = AuthorizeInteractor(fetchAccessTokenUseCase)

    companion object {

        const val REDIRECT_URL = "http://localhost:8080"
        const val STATE_STRING = "should-be-random-lole"

        @JvmStatic
        fun codes() = listOf(
            "Zfqo91v2pt-DlFclsbfIruV6yAlVpQ#_",
            "a1b2c3",
            "A1b2C3",
        )
    }

    @ParameterizedTest
    @MethodSource("codes")
    fun `correct url EXPECT fetch access token`(code: String) = runTest {
        val url = getUrl(code = code)

        authorizeInteractor(url)

        coVerify { fetchAccessTokenUseCase(code) }
    }

    @Test
    fun `redirect url is wrong EXPECT throw auth exception`() = runTest {
        val url = getUrl(redirectUrl = "poop lole")

        assertThrows<AuthException> {
            authorizeInteractor(url)
        }
    }

    @Test
    fun `state is wrong EXPECT throw auth exception`() = runTest {
        val url = getUrl(state = "poop-lole")

        assertThrows<AuthException> {
            authorizeInteractor(url)
        }
    }

    @Test
    fun `code is empty EXPECT throw auth exception`() = runTest {
        val url = getUrl(code = "")

        assertThrows<AuthException> {
            authorizeInteractor(url)
        }
    }

    private fun getUrl(
        redirectUrl: String = REDIRECT_URL,
        state: String = STATE_STRING,
        code: String = "code",
    ): String =
        "$redirectUrl/?state=$state&code=$code"
}