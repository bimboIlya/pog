package com.bimboilya.navsample.common.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object CommandDispatcher {

    private val commandChannel = Channel<Command>(Channel.CONFLATED)
    val commandFlow = commandChannel.receiveAsFlow()

    fun dispatch(command: Command) {
        commandChannel.trySend(command)
    }
}