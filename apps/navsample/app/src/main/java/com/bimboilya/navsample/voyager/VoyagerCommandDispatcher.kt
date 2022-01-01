package com.bimboilya.navsample.voyager

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object VoyagerCommandDispatcher {

    private val commandChannel = Channel<VoyagerCommand>(Channel.CONFLATED)
    val commandFlow = commandChannel.receiveAsFlow()

    fun dispatch(command: VoyagerCommand) {
        commandChannel.trySend(command)
    }
}