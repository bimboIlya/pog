package com.bimboilya.common.navigation

interface AppRouter {

    fun open(destination: Destination)

    fun open(destinationRoute: Route)

    fun replace(destinationRoute: Route)

    /**
     * start: [A] - [B] - [C] - [D]
     * replace(E, B.route)
     * end: [A] - [B] - [E]
     */
    fun replace(destinationRoute: Route, routeToPopUpTo: String)

    fun pop()

    fun popUpTo(destinationRoute: String, inclusive: Boolean = false)

    fun popToRoot(inclusive: Boolean = false)
}
