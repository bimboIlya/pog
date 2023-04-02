package com.bimboilya.bluepoop.feature.scan.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.StartScanClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanState
import com.bimboilya.bluepoop.feature.scan.presentation.ScanState.Initial
import com.bimboilya.bluepoop.feature.scan.presentation.ScanViewModel
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle
import com.bimboilya.common.ui.components.LoadingTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun ScanScreen() {
  val viewModel: ScanViewModel = hiltViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      ScanAppBar(
        inProgress = state.inProgress,
        isRetryVisible = state !is Initial,
        onRetryClick = { viewModel.handleAction(StartScanClicked) },
      )
    },
    content = { paddingValues ->
      ScanContent(
        state = state,
        onAction = viewModel::handleAction,
        modifier = Modifier.padding(paddingValues)
      )
    },
  )
}

@Composable
private fun ScanAppBar(
  inProgress: Boolean,
  isRetryVisible: Boolean,
  onRetryClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  LoadingTopBar(
    isProgressVisible = inProgress,
    title = { Text("Scanning") },
    actions = {
      if (isRetryVisible) {  // add pause button
        IconButton(onClick = onRetryClick) {
          Icon(
            painter = rememberVectorPainter(Icons.Default.Refresh),
            contentDescription = null,
          )
        }
      }
    },
    modifier = modifier,
  )
}

@Composable
private fun ScanContent(
  state: ScanState,
  onAction: (ScanAction) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Button(onClick = { onAction(StartScanClicked) }) {
      Text(text = "text")
    }
  }
}
