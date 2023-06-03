package com.bimboilya.common.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bimboilya.common.ui.components.insets.TopAppBar

@Composable
fun LoadingTopBar(
  isProgressVisible: Boolean,
  title: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationIcon: @Composable (() -> Unit)? = null,
  actions: @Composable RowScope.() -> Unit = {},
  backgroundColor: Color = MaterialTheme.colors.primarySurface,
  contentColor: Color = contentColorFor(backgroundColor),
  elevation: Dp = AppBarDefaults.TopAppBarElevation,
) {
  Box(modifier) {
    TopAppBar(
      title = title,
      navigationIcon = navigationIcon,
      actions = actions,
      backgroundColor = backgroundColor,
      contentColor = contentColor,
      elevation = elevation,
    )

    AnimatedVisibility(visible = isProgressVisible, Modifier.align(Alignment.BottomCenter)) {
      LinearProgressIndicator(Modifier.fillMaxWidth())
    }
  }
}
