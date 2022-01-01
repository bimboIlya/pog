package com.bimboilya.navsample.common

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
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
    topContent: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
) {
    navAction?.let {
        BackHandler(onBack = navAction)
    }

    Scaffold(
        topBar = { AppBar(title, navIcon, navAction) }
    ) { padding ->
        Box(
            Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                var checked by rememberSaveable { mutableStateOf(false) }
                Switch(checked, onCheckedChange = { checked = it })
                Spacer(Modifier.width(4.dp))
                Text(text = "Changed state representation")
            }
        }
        Column(
            Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(Modifier.fillMaxWidth().weight(1f)) { topContent() }
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { genericAction?.invoke() }, enabled = genericAction != null) {
                    Text(text = genericButtonText)
                }
                Button(onClick = { specialAction?.invoke() }, enabled = specialAction != null) {
                    Text(text = specialButtonText)
                }
            }
            Box(Modifier.fillMaxWidth().weight(1f)) { bottomContent() }
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
        navigationIcon = navigationIcon(navIcon, navIconClick)
    )
}

@Composable
private fun navigationIcon(navIcon: NavIcon?, navIconClick: (() -> Unit)?): @Composable (() -> Unit)? {
    if (navIcon == null || navIconClick == null) {
        return null
    } else return {
        IconButton(
            onClick = navIconClick,
            content = {
                Icon(imageVector = navIcon.icon, contentDescription = null)
            }
        )
    }
}
