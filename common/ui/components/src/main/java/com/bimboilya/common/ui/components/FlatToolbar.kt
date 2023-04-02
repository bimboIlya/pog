package com.bimboilya.common.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bimboilya.common.ui.components.insets.TopAppBar

@Composable
fun FlatToolbar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    addStatusBarPadding: Boolean = true,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        addStatusBarPadding = addStatusBarPadding,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    )
}
