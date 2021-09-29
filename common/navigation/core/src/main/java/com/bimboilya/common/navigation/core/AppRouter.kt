package com.bimboilya.common.navigation.core

interface AppRouter {

    fun open(destination: Destination)

    fun replace(destination: Destination)

    /**
     * start: [A] - [B] - [C] - [D]
     * replace(E, B)
     * end: [A] - [B] - [E]
     */
    fun replace(destination: Destination, destinationToPopUpTo: Destination)

    fun pop()

    fun popUpTo(destination: Destination, inclusive: Boolean = false)

    fun popToRoot(inclusive: Boolean = false)
}
