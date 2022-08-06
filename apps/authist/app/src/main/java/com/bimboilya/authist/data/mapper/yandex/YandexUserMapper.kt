package com.bimboilya.authist.data.mapper.yandex

import com.bimboilya.authist.data.SocialAccount
import com.bimboilya.authist.domain.entity.SocialNetwork.YANDEX
import com.bimboilya.authist.domain.entity.User
import javax.inject.Inject

class YandexUserMapper @Inject constructor() {

    fun map(yandexAccount: SocialAccount.Yandex): User =
        User(
            id = "",
            name = "",
            email = "",
            token = yandexAccount.token,
            socialNetwork = YANDEX,
        )
}
