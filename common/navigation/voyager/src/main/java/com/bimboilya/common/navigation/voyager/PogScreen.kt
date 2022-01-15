package com.bimboilya.common.navigation.voyager

import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import kotlin.reflect.KClass

interface PogScreen : Screen {

    val associatedDestination: KClass<out Destination>

    override val key: ScreenKey
        get() = requireNotNull(associatedDestination.qualifiedName)
}

abstract class VmScreen : AndroidScreen() {

    abstract val associatedDestination: KClass<out Destination>

    override val key: ScreenKey
        get() = requireNotNull(associatedDestination.qualifiedName)

}