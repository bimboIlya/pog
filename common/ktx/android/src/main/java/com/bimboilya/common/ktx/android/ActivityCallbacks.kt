package com.bimboilya.common.ktx.android

import android.app.Activity
import android.app.Application
import android.os.Bundle

fun ActivityLifecycleCallbacks(
    onActivityCreated: (activity: Activity, savedInstanceState: Bundle?) -> Unit = { _, _ -> },
    onActivityStarted: (activity: Activity) -> Unit = { },
    onActivityResumed: (activity: Activity) -> Unit = { },
    onActivityPaused: (activity: Activity) -> Unit = { },
    onActivityStopped: (activity: Activity) -> Unit = { },
    onActivitySaveInstanceState: (activity: Activity, outState: Bundle) -> Unit = { _, _ -> },
    onActivityDestroyed: (activity: Activity) -> Unit = { },
) = object : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        onActivityCreated(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
        onActivityStarted(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        onActivityResumed(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        onActivityPaused(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        onActivityStopped(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        onActivitySaveInstanceState(activity, outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
        onActivityDestroyed(activity)
    }
}
