package com.bimboilya.bluepoop.feature.scan.presentation

sealed interface ScanAction {
  object StartScanClicked : ScanAction
}
