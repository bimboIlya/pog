package com.bimboilya.navsample.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class NavIcon(val icon: ImageVector) {

    Back(icon = Icons.Default.ArrowBack),
    Close(icon = Icons.Filled.Close)
}

@Composable
fun GenericScreen(
    title: String,
    navIcon: NavIcon? = null,
    navAction: (() -> Unit)? = null,
    genericButtonText: String = "Generic action",
    genericAction: (() -> Unit)? = null,
    specialButtonText: String = "Special action",
    specialAction: (() -> Unit)? = null,
) {
    Scaffold(
        topBar = { AppBar(title, navIcon, navAction) }
    ) { padding ->
        Box(
            Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                var checked by remember { mutableStateOf(false) }
                Switch(checked, onCheckedChange = { checked = it })
                Spacer(Modifier.width(4.dp))
                Text(text = "Changed state representation")
            }
        }
        Column(
            Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.weight(1f))
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                if (genericAction != null) {
                    Button(onClick = genericAction) {
                        Text(text = genericButtonText)
                    }
                }
                if (specialAction != null) {
                    Button(onClick = specialAction) {
                        Text(text = specialButtonText)
                    }
                }
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun AppBar(
    title: String,
    navIcon: NavIcon? = null,
    navIconClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (navIcon != null && navIconClick != null) {
                IconButton(onClick = navIconClick, content = { Icon(imageVector = navIcon.icon, contentDescription = null) })
            }
        }
    )
}
