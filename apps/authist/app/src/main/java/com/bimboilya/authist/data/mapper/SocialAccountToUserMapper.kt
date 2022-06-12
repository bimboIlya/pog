package com.bimboilya.authist.data.mapper

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.authist.data.SocialAccount.Google
import com.bimboilya.authist.data.SocialAccount.Vk
import com.bimboilya.authist.data.mapper.google.GoogleUserMapper
import com.bimboilya.authist.data.mapper.vk.VkUserMapper
import com.bimboilya.authist.domain.entity.User
import javax.inject.Inject

class SocialAccountToUserMapper @Inject constructor(
    private val googleUserMapper: GoogleUserMapper,
    private val vkUserMapper: VkUserMapper,
) {

    fun map(account: SocialAccount): User =
        when (account) {
            is Google -> googleUserMapper.map(account)
            is Vk -> vkUserMapper.map(account)
        }
}
