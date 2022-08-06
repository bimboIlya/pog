package com.bimboilya.authist.data.mapper

import com.bimboilya.authist.data.mapper.google.GoogleErrorMapper
import com.bimboilya.authist.data.mapper.vk.VkErrorMapper
import com.bimboilya.authist.domain.entity.AuthError
import com.google.android.gms.common.api.ApiException
import com.vk.api.sdk.exceptions.VKAuthException
import com.yandex.authsdk.YandexAuthException
import javax.inject.Inject

class AuthErrorMapper @Inject constructor(
    private val googleErrorMapper: GoogleErrorMapper,
    private val vkErrorMapper: VkErrorMapper,
) {

    fun map(error: Throwable): Throwable =
        when (error) {
            is ApiException -> googleErrorMapper.map(error)
            is VKAuthException -> vkErrorMapper.map(error)
            is YandexAuthException -> AuthError.Unknown(error) // pretend it's yandex error mapper
            else -> error
        }
}
