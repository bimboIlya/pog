package com.bimboilya.bluepoop.feature.scan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.DeviceItemClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.StartScanClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.StopScanClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanState.Results
import com.bimboilya.bluepoop.feature.scan.presentation.ScanningEvent.Error
import com.juul.kable.Advertisement
import com.juul.kable.Scanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ScanViewModel @Inject constructor(
  private val scanner: Scanner,
) : ViewModel() {

  private val _state = MutableStateFlow<ScanState>(ScanState.Initial)
  val state = _state.asStateFlow()

  private val _events = Channel<ScanningEvent>(Channel.UNLIMITED)
  val events = _events.receiveAsFlow()

  private var scanJob: Job? = null

  fun handleAction(action: ScanAction) {
    when (action) {
      is StartScanClicked -> startScanning()
      is StopScanClicked -> stopScanning()
      is DeviceItemClicked -> {}
    }
  }

  private fun startScanning() {
    _state.update { Results(emptyList(), inProgress = true) }

    stopScanning()

    scanJob = scanner.advertisements
      .onCompletion { updateProgressState(inProgress = false) }
      .scan(emptyList<Advertisement>()) { acc, advertisement -> acc + advertisement }
      .onEach(::updateItems)
      .catch { e -> handleError(e) }
      .launchWithTimeout()
  }

  private fun updateProgressState(inProgress: Boolean) {
    updateResultsState { state -> state.copy(inProgress = inProgress) }
  }

  private fun updateItems(advertisements: List<Advertisement>) {
    updateResultsState { state ->
      val distinctAdvertisements = advertisements.distinctBy(Advertisement::address)
      state.copy(items = distinctAdvertisements.toDeviceItems())
    }
  }

  private fun List<Advertisement>.toDeviceItems(): List<DeviceItem> =
    map { advertisement ->
      DeviceItem(
        address = advertisement.address,
        name = advertisement.name ?: "Unknown name",
      )
    }

  private fun updateResultsState(function: (Results) -> Results) {
    _state.update { state -> if (state is Results) function(state) else state }
  }

  private fun handleError(error: Throwable) {
    stopScanning()
    Timber.e(error)
    _events.trySend(Error(error::class.simpleName ?: "Unknown"))
  }

  private fun <T> Flow<T>.launchWithTimeout(timeout: Duration = DefaultScanTimeout): Job =
    viewModelScope.launch {
      withTimeout(timeout) { collect() }
    }

  private fun stopScanning() {
    scanJob?.cancel()
    scanJob = null
  }

  override fun onCleared() {
    stopScanning()
  }

  companion object {
    private val DefaultScanTimeout = 20.seconds
  }
}
