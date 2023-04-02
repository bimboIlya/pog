package com.bimboilya.bluepoop.shared.navigation

import com.ramcosta.composedestinations.spec.Direction

sealed interface NavigationCommand {
  object Pop : NavigationCommand
  data class Open(val direction: Direction) : NavigationCommand
}
