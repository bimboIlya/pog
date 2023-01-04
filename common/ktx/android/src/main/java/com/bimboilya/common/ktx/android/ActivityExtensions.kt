package com.bimboilya.common.ktx.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun Activity.hideKeyboard(clearFocus: Boolean = false) {
    val currentFocus = window.currentFocus
    val imm = getSystemService<InputMethodManager>()

    if (clearFocus) {
        currentFocus?.clearFocus()
    }

    imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.openSettings() {
    Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
        .apply { data = Uri.parse("package:$packageName") }
        .let(::startActivity)
}
