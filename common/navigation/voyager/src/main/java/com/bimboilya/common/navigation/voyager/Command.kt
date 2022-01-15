package com.bimboilya.common.navigation.voyager

import kotlin.reflect.KClass

sealed class Command

class Open(val destination: Destination) : Command()

class Replace(val destination: Destination) : Command()

object Pop : Command()

object PopToRoot : Command()

class PopTo(val destinationClass: KClass<out Destination>) : Command()
