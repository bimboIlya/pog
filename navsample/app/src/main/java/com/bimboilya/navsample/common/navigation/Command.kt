package com.bimboilya.navsample.common.navigation

sealed class Command {

    class Open(val destination: Destination) : Command()

    class OpenRoute(val route: String) : Command()

    class Replace(val destination: Destination) : Command()

    class ReplaceRoute(val route: String) : Command()

    object Pop : Command()

    class PopUpTo(val destination: Destination) : Command()

    object PopUpToRoot : Command()

    object CloseFlow : Command()

    class Composite(val commands: List<Command>) : Command()
}
