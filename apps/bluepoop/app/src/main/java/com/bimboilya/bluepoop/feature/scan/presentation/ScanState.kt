package com.bimboilya.bluepoop.feature.scan.presentation

sealed interface ScanState {
  val inProgress: Boolean

  object Initial : ScanState {
    override val inProgress = false
  }

  data class Results(
    val items: List<Int>,
    override val inProgress: Boolean,
  ) : ScanState
}
