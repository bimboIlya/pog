package com.bimboilya.authist.data.mapper.vk

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.authist.domain.entity.SocialNetwork.VK
import com.bimboilya.authist.domain.entity.User
import javax.inject.Inject

class VkUserMapper @Inject constructor() {

    fun map(vkAccount: SocialAccount.Vk): User =
        with(vkAccount.value) {
            User(
                id = userId.toString(),
                name = "",
                email = email.orEmpty(),
                token = accessToken,
                socialNetwork = VK,
            )
        }
}
