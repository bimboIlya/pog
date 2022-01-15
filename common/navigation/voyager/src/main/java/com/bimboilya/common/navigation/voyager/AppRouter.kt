package com.bimboilya.common.navigation.voyager

import javax.inject.Inject
import kotlin.reflect.KClass

interface AppRouter {

    fun open(destination: Destination)

    fun replace(destination: Destination)

    fun pop(destination: Destination)

    fun popToRoot()

    fun popTo(destinationClass: KClass<out Destination>)
}

class AppRouterImpl @Inject constructor(
    private val dispatcher: CommandDispatcher
) : AppRouter {

    override fun open(destination: Destination) {
        dispatcher.dispatch(Open(destination))
    }

    override fun replace(destination: Destination) {
        dispatcher.dispatch(Replace(destination))
    }

    override fun pop(destination: Destination) {
        dispatcher.dispatch(Pop)
    }

    override fun popToRoot() {
        dispatcher.dispatch(PopToRoot)
    }

    override fun popTo(destinationClass: KClass<out Destination>) {
        dispatcher.dispatch(PopTo(destinationClass))
    }
}