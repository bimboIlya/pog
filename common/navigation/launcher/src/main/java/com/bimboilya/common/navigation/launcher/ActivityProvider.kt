package com.bimboilya.common.navigation.launcher

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import com.bimboilya.common.ktx.android.ActivityLifecycleCallbacks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.lang.ref.WeakReference
import kotlin.properties.Delegates.notNull

object ActivityProvider {

    private val activityReferenceFlow = MutableStateFlow<WeakReference<ComponentActivity>>(WeakReference(null))

    var appContext: Context by notNull()
        private set

    val activity: ComponentActivity?
        get() = activityReferenceFlow.value.get()

    fun observeActivity(): Flow<ComponentActivity> =
        activityReferenceFlow.map { it.get() }
            .distinctUntilChanged { old, new -> old === new }
            .filterNotNull()

    fun init(app: Application) {
        appContext = app

        app.registerActivityLifecycleCallbacks(
            ActivityLifecycleCallbacks(
                onActivityCreated = { activity, _ -> attachActivity(activity) },
                onActivityResumed = ::attachActivity,
                onActivityDestroyed = ::detachActivity
            )
        )
    }

    private fun attachActivity(activity: Activity) {
        if (activity is ComponentActivity) activityReferenceFlow.tryEmit(WeakReference(activity))
    }

    private fun detachActivity(activity: Activity) {
        if (this.activity === activity) activityReferenceFlow.value.clear()
    }
}
