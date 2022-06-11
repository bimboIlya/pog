package com.bimboilya.authist

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

object ActivityProvider : DefaultLifecycleObserver {

    private val _activity: MutableStateFlow<ComponentActivity?> = MutableStateFlow(null)
    val activity = _activity
        .distinctUntilChanged { old, new -> old === new }
        .filterNotNull()

    override fun onCreate(owner: LifecycleOwner) {
        (owner as? ComponentActivity)?.let(_activity::value::set)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _activity.value = null
    }
}
