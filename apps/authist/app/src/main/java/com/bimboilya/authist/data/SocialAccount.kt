package com.bimboilya.authist.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.vk.api.sdk.auth.VKAccessToken

sealed interface SocialAccount {

    class Google(val value: GoogleSignInAccount) : SocialAccount

    class Vk(val value: VKAccessToken) : SocialAccount
}
