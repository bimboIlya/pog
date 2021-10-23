package com.bimboilya.common.ktx.android

import android.app.Activity
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
