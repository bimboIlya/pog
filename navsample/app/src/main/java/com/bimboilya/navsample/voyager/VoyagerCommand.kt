package com.bimboilya.navsample.voyager

import cafe.adriel.voyager.core.screen.Screen
import kotlin.reflect.KClass

sealed class VoyagerCommand

class Open(val destination: VoyagerDestination) : VoyagerCommand()

class Replace(val destination: VoyagerDestination) : VoyagerCommand()

object Pop : VoyagerCommand()

object PopToRoot : VoyagerCommand()

class PopTo(val screenClass: KClass<out Screen>) : VoyagerCommand()

class CloseFlow(val userFlow: UserFlow) : VoyagerCommand()

class Composite(val commands: List<VoyagerCommand>) : VoyagerCommand()
