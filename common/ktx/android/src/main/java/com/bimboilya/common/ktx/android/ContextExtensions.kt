package com.bimboilya.common.ktx.android

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline fun <reified A : Activity> Context.startActivity() {
    startActivity(Intent(this, A::class.java))
}

fun Context.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.broadcastFlow(intentFilter: IntentFilter): Flow<Intent> =
    callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let(::trySend)
            }
        }

        registerReceiver(receiver, intentFilter)

        awaitClose { unregisterReceiver(receiver) }
    }
