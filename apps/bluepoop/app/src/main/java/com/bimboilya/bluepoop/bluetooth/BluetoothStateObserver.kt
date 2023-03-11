package com.bimboilya.bluepoop.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.IntentFilter
import com.bimboilya.common.ktx.android.broadcastFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface BluetoothStateObserver {
    fun getState(): BluetoothState
    fun observeState(): Flow<BluetoothState>
}

class BluetoothStateObserverImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    externalScope: CoroutineScope,
) : BluetoothStateObserver {

    private val state = MutableStateFlow(appContext.bluetoothAdapter.state.toBluetoothState())

    init {
        appContext.broadcastFlow(IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
            .map { intent -> intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR).toBluetoothState() }
            .onEach(state::tryEmit)
            .catch { /* ignore */ }
            .launchIn(externalScope)
    }

    override fun getState(): BluetoothState =
        state.value

    override fun observeState(): Flow<BluetoothState> =
        state.asStateFlow()
}
