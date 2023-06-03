package com.bimboilya.bluepoop.feature.scan.presentation

sealed interface ScanAction {
  object StartScanClicked : ScanAction
  object StopScanClicked : ScanAction
  data class DeviceItemClicked(val address: String) : ScanAction
}
