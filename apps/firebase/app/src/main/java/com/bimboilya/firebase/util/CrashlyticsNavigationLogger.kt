package com.bimboilya.firebase.util

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class CrashlyticsNavigationLogger @Inject constructor() {

    private companion object {
        const val NAVIGATION_BACKSTACK_KEY = "NAVIGATION_BACKSTACK_KEY"
    }

    fun setCurrentNavBackStackRoutes(navBackStackRoutes: List<String>) {
        val navBackStackString = navBackStackRoutes.joinToString(separator = " → ")

        Firebase.crashlytics.setCustomKey(NAVIGATION_BACKSTACK_KEY, navBackStackString)
    }
}