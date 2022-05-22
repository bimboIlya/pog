package com.bimboilya.firebase.feature.notification

import com.bimboilya.firebase.feature.notification.util.log
import com.bimboilya.firebase.feature.notification.util.logMessage
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    init {
        log("service initialized")
    }

    override fun onNewToken(token: String) {
        log("New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        logMessage(message)
    }
}