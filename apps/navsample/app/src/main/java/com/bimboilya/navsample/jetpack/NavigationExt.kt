package com.bimboilya.navsample.jetpack

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.composable(destination: ComposableDestination) {
    composable(
        route = destination.getRoute(),
    ) { backStackEntry ->
        destination.CreateComposable(backStackEntry)
    }
}

fun NavGraphBuilder.composable(destination: ComposableWithArgsDestination) {
    composable(
        route = destination.getRoute(),
        arguments = destination.getArguments(),
    ) { backStackEntry ->
        destination.CreateComposable(backStackEntry)
    }
}

inline fun <reified T> navArgument(
    name: String,
    isNullable: Boolean = false,
): List<NamedNavArgument> =
    listOf(
        navArgument(name) {
            val argType = T::class
            type = when (argType) {
                Boolean::class -> NavType.BoolType
                Int::class -> NavType.IntType
                Long::class -> NavType.LongType
                Float::class -> NavType.FloatType
                String::class -> NavType.StringType
                else -> throw IllegalArgumentException("Unsupported argument type")
            }
            nullable = isNullable
        }
    )