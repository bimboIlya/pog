package com.bimboilya.common.navigation.compose.extenstions

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bimboilya.common.navigation.compose.ComposableDestination

fun NavGraphBuilder.composable(destination: ComposableDestination) {
    composable(destination.getRoute(), destination.getArguments()) { backStackEntry ->
        destination.CreateComposable(backStackEntry)
    }
}

fun NavGraphBuilder.addDestinations(destinations: Iterable<ComposableDestination>) {
    destinations.forEach(::composable)
}

inline fun <reified T> singleNavArgument(
    name: String,
    isNullable: Boolean = false,
    default: T? = null,
): List<NamedNavArgument> =
    listOf(
        navArgument(name) {
            val argType = T::class
            this.type = when (argType) {
                Boolean::class -> NavType.BoolType
                Int::class -> NavType.IntType
                Long::class -> NavType.LongType
                Float::class -> NavType.FloatType
                String::class -> NavType.StringType
                else -> throw IllegalArgumentException("Unsupported argument type")
            }
            this.nullable = isNullable
            default?.let { defaultValue = it }
        }
    )
