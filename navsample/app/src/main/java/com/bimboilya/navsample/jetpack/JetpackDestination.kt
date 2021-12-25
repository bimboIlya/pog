package com.bimboilya.navsample.jetpack

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import com.bimboilya.navsample.common.navigation.Destination

interface JetpackDestination : Destination {

    fun getRoute(): String = javaClass.simpleName
}

/**
 * [ComposableDestination] и [ComposableWithArgsDestination] разделены намерено, чтобы
 * была возможность разделить их вызов в навигаторе
 * Нужно это для того, чтобы по ошибке не открывать Destination с ключом аргумента
 */
interface ComposableDestination : JetpackDestination {

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)
}

interface ComposableWithArgsDestination : JetpackDestination {

    fun getArguments(): List<NamedNavArgument> = emptyList()

    @Composable
    fun CreateComposable(backStackEntry: NavBackStackEntry)

}

interface TabDestination : JetpackDestination

interface FlowDestination : JetpackDestination