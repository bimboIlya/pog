package com.bimboilya.navsample.jetpack

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry

interface Destination

interface ActivityDestination : Destination {

    fun createIntent(context: Context): Intent
}

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