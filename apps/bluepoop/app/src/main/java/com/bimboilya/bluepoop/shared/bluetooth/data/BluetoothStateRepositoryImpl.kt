package com.bimboilya.bluepoop.shared.bluetooth.data

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.IntentFilter
import com.bimboilya.bluepoop.shared.bluetooth.bluetoothAdapter
import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothState
import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothStateRepository
import com.bimboilya.bluepoop.shared.bluetooth.domain.toBluetoothState
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

class BluetoothStateRepositoryImpl @Inject constructor(
  @ApplicationContext private val appContext: Context,
  externalScope: CoroutineScope,
) : BluetoothStateRepository {

  private val state = MutableStateFlow(appContext.bluetoothAdapter.state.toBluetoothState())

  init {
    appContext.broadcastFlow(IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
      .map { intent -> intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR).toBluetoothState() }
      .onEach(state::tryEmit)
      .catch { /* ignore */ }
      .launchIn(externalScope)
  }

  override fun get(): BluetoothState =
    state.value

  override fun observe(): Flow<BluetoothState> =
    state.asStateFlow()
}
