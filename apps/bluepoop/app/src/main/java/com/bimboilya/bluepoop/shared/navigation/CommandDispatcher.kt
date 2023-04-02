package com.bimboilya.bluepoop.shared.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class CommandDispatcher @Inject constructor() {

  private val channel = Channel<NavigationCommand>(Channel.UNLIMITED)

  fun dispatch(command: NavigationCommand) {
    channel.trySend(command)
  }

  fun observe(): Flow<NavigationCommand> =
    channel.receiveAsFlow()
}
