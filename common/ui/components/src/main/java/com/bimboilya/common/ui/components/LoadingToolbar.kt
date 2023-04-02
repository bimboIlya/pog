package com.bimboilya.common.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bimboilya.common.ui.components.insets.TopAppBar

@OptIn(ExperimentalAnimationApi::class)
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
  Column(modifier) {
    TopAppBar(
      title = title,
      navigationIcon = navigationIcon,
      actions = actions,
      backgroundColor = backgroundColor,
      contentColor = contentColor,
      elevation = elevation,
    )

    AnimatedContent(targetState = isProgressVisible) { visible ->
      when (visible) {
        true -> LinearProgressIndicator(Modifier.fillMaxWidth())
        false -> Spacer(Modifier.height(ProgressIndicatorDefaults.StrokeWidth))
      }
    }
  }
}
