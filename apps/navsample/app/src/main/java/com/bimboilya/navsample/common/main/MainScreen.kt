package com.bimboilya.navsample.common.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.bimboilya.navsample.common.main.BottomNavScreen.Favorite
import com.bimboilya.navsample.common.main.BottomNavScreen.Home
import com.bimboilya.navsample.common.main.BottomNavScreen.Menu

sealed class BottomNavScreen(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
) {
    object Home : BottomNavScreen("Home", Icons.Outlined.Home, Icons.Filled.Home)
    object Favorite : BottomNavScreen("Favorite", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)
    object Menu : BottomNavScreen("Menu", Icons.Outlined.Menu)
}

@Composable
fun MainScreen(
    currentScreen: BottomNavScreen,
    onScreenSelected: (BottomNavScreen) -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = { BottomBar(currentScreen, onScreenSelected) }
    ) { padding ->
        Box(Modifier.fillMaxWidth().padding(padding)) {
            content()
        }
    }
}

@Composable
private fun BottomBar(
    currentScreen: BottomNavScreen,
    onScreenSelected: (BottomNavScreen) -> Unit,
) {
    BottomNavigation {
        BottomNavItem(screen = Home, currentScreen, onClick = onScreenSelected)
        BottomNavItem(screen = Favorite, currentScreen, onClick = onScreenSelected)
        BottomNavItem(screen = Menu, currentScreen, onClick = onScreenSelected)
    }
}

@Composable
private fun RowScope.BottomNavItem(
    screen: BottomNavScreen,
    currentScreen: BottomNavScreen,
    onClick: (BottomNavScreen) -> Unit,
) {
    val selected = screen == currentScreen

    BottomNavigationItem(
        selected,
        onClick = { onClick(screen) },
        icon = { BottomNavIcon(screen, selected) },
        label = { Text(text = screen.label) }
    )
}

@Composable
private fun BottomNavIcon(screen: BottomNavScreen, selected: Boolean) {
    val icon = if (selected && screen.selectedIcon != null) screen.selectedIcon else screen.icon
    Crossfade(targetState = icon) {
        Icon(it, contentDescription = screen.label)
    }
}