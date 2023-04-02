package com.bimboilya.bluepoop.feature.scan.presentation

import androidx.lifecycle.ViewModel
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.StartScanClicked
import com.juul.kable.Scanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
  private val scanner: Scanner,
) : ViewModel() {

  private val _state = MutableStateFlow<ScanState>(ScanState.Initial)
  val state = _state.asStateFlow()

  fun handleAction(action: ScanAction) {
    when(action) {
      StartScanClicked -> _state.update { ScanState.Results(emptyList(), inProgress = true) }
    }
  }
}
