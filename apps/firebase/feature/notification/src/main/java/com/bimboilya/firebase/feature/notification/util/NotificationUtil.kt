package com.bimboilya.firebase.feature.notification.util

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat

fun buildNotification(
    context: Context,
    channelId: String,
    block: NotificationCompat.Builder.() -> Unit,
): Notification =
    NotificationCompat.Builder(context, channelId).apply(block).build()