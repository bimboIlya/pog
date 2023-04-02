package com.bimboilya.bluepoop.shared.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate

class BluepoopNavigator(
  private val navController: NavController,
) {

  fun executeCommand(command: NavigationCommand) {
    when (command) {
      NavigationCommand.Pop -> navController.navigateUp()
      is NavigationCommand.Open -> navController.navigate(command.direction)
    }
  }
}
