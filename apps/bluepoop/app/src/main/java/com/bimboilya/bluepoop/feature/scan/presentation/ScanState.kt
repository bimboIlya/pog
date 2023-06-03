package com.bimboilya.bluepoop.feature.scan.presentation

import com.bimboilya.bluepoop.feature.scan.presentation.ToolbarButtonType.None
import com.bimboilya.bluepoop.feature.scan.presentation.ToolbarButtonType.Retry
import com.bimboilya.bluepoop.feature.scan.presentation.ToolbarButtonType.Stop

sealed interface ScanState {
  val inProgress: Boolean

  val toolbarButtonType: ToolbarButtonType
    get() = when {
      inProgress -> Stop
      this is Results -> Retry
      else -> None
    }

  object Initial : ScanState {
    override val inProgress = false
  }

  data class Results(
    val items: List<DeviceItem>,
    override val inProgress: Boolean,
  ) : ScanState
}

enum class ToolbarButtonType {
  None, Retry, Stop
}
