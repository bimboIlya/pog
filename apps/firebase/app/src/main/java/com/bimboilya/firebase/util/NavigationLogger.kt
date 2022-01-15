package com.bimboilya.firebase.util

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class NavigationLogger @Inject constructor() {

    private companion object {
        const val NAVIGATION_BACKSTACK_KEY = "NAVIGATION_BACKSTACK_KEY"
    }

    fun setCurrentNavBackStack(navBackStack: List<String>) {
        val navBackStackString = navBackStack.joinToString(separator = " → ")

        Firebase.crashlytics.setCustomKey(NAVIGATION_BACKSTACK_KEY, navBackStackString)
    }
}