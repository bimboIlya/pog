package com.bimboilya.bluepoop.feature.scan.presentation

sealed interface ScanningEvent {
  data class Error(val name: String) : ScanningEvent
}
