package com.bimboilya.authist.data.mapper.vk

import com.bimboilya.authist.domain.entity.AuthError
import com.vk.api.sdk.exceptions.VKAuthException
import javax.inject.Inject

class VkErrorMapper @Inject constructor() {

    fun map(vkError: VKAuthException): AuthError =
        when {
            vkError.isCanceled -> AuthError.Cancelled(vkError)
            else -> AuthError.Unknown(vkError)
        }
}
