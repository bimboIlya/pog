package com.bimboilya.navsample.jetpack

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object JetpackCommandDispatcher {

    private val commandChannel = Channel<JetpackCommand>(Channel.CONFLATED)
    val commandFlow = commandChannel.receiveAsFlow()

    fun dispatch(command: JetpackCommand) {
        commandChannel.trySend(command)
    }
}