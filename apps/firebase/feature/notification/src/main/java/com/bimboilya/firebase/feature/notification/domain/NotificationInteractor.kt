package com.bimboilya.firebase.feature.notification.domain

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationInteractor @Inject constructor() {

    private val messaging = Firebase.messaging

    suspend fun getToken(): String =
        messaging.token.await()

    suspend fun deleteToken() {
        messaging.deleteToken().await()
    }
}