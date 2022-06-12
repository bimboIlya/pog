package com.bimboilya.authist.data.mapper

import com.bimboilya.authist.data.mapper.google.GoogleErrorMapper
import com.bimboilya.authist.data.mapper.vk.VkErrorMapper
import com.google.android.gms.common.api.ApiException
import com.vk.api.sdk.exceptions.VKAuthException
import javax.inject.Inject

class AuthErrorMapper @Inject constructor(
    private val googleErrorMapper: GoogleErrorMapper,
    private val vkErrorMapper: VkErrorMapper,
) {

    fun map(error: Throwable): Throwable =
        when (error) {
            is ApiException -> googleErrorMapper.map(error)
            is VKAuthException -> vkErrorMapper.map(error)
            else -> error
        }
}
