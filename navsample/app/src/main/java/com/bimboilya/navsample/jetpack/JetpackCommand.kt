package com.bimboilya.navsample.jetpack

sealed class JetpackCommand {

    class Open(val destination: Destination) : JetpackCommand()

    class OpenRoute(val route: String) : JetpackCommand()

    class Replace(val destination: Destination) : JetpackCommand()

    class ReplaceRoute(val route: String) : JetpackCommand()

    object Pop : JetpackCommand()

    class PopUpTo(val destination: Destination) : JetpackCommand()

    object PopUpToRoot : JetpackCommand()

    object CloseFlow : JetpackCommand()

    class Composite(val commands: List<JetpackCommand>) : JetpackCommand()
}
