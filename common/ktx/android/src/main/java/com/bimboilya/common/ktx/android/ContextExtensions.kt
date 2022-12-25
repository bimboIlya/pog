package com.bimboilya.common.ktx.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

inline fun <reified A : Activity> Context.startActivity() {
    startActivity(Intent(this, A::class.java))
}

fun Context.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
