package com.bimboilya.common.navigation.voyager

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

interface CommandDispatcher {

    val commandFlow: Flow<Command>

    fun dispatch(command: Command)
}

class CommandDispatcherImpl @Inject constructor() : CommandDispatcher {

    private val commandChannel = Channel<Command>(Channel.CONFLATED)
    override val commandFlow = commandChannel.receiveAsFlow()

    override fun dispatch(command: Command) {
        commandChannel.trySend(command)
    }
}