package com.bimboilya.firebase.feature.notification.util

import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

internal fun log(message: String) {
    Timber.tag("fb_notification").d(message)
}

internal fun logMessage(message: RemoteMessage) {
    val formatter = SimpleDateFormat("hh:mm:ss DD.MM", Locale.ENGLISH)
    val date = Date(message.sentTime)
    """
        id: ${message.messageId}
        from: ${message.from}
        type: ${message.messageType}
        senderId: ${message.senderId}
        date: ${formatter.format(date)}
        priority: ${message.priority} | original priority: ${message.originalPriority}
        date: ${message.data}
    """
        .trimIndent()
        .let(::log)
}