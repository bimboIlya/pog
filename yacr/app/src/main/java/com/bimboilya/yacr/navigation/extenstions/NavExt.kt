package com.bimboilya.yacr.navigation.extenstions

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.bimboilya.yacr.navigation.NavDirection

fun NavGraphBuilder.composable(direction: NavDirection) {
    composable(direction.route.value, direction.getArguments()) { backStackEntry ->
        direction.CreateComposable(backStackEntry)
    }
}

fun NavGraphBuilder.addNavDirections(directions: Iterable<NavDirection>) {
    directions.forEach(::composable)
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
