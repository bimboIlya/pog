package com.bimboilya.common.navigation.core

interface AppRouter {

    fun open(destination: Destination)

    fun open(destinationRoute: String)

    fun replace(destinationRoute: String)

    /**
     * start: [A] - [B] - [C] - [D]
     * replace(E, B.route)
     * end: [A] - [B] - [E]
     */
    fun replace(destinationRoute: String, routeToPopUpTo: String)

    fun pop()

    fun popUpTo(destinationRoute: String, inclusive: Boolean = false)

    fun popToRoot(inclusive: Boolean = false)
}
