package com.bimboilya.bluepoop.feature.scan.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bimboilya.bluepoop.feature.scan.presentation.DeviceItem
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.DeviceItemClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.StartScanClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanAction.StopScanClicked
import com.bimboilya.bluepoop.feature.scan.presentation.ScanState
import com.bimboilya.bluepoop.feature.scan.presentation.ScanState.Initial
import com.bimboilya.bluepoop.feature.scan.presentation.ScanState.Results
import com.bimboilya.bluepoop.feature.scan.presentation.ScanViewModel
import com.bimboilya.bluepoop.feature.scan.presentation.ScanningEvent.Error
import com.bimboilya.bluepoop.feature.scan.presentation.ToolbarButtonType
import com.bimboilya.bluepoop.feature.scan.presentation.ToolbarButtonType.*
import com.bimboilya.common.ktx.compose.collectInComposition
import com.bimboilya.common.ui.components.LoadingTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun ScanScreen() {
  val viewModel: ScanViewModel = hiltViewModel()
  val state by viewModel.state.collectAsStateWithLifecycle()

  val scaffoldState = rememberScaffoldState()
  viewModel.events.collectInComposition { event ->
    when (event) {
      is Error -> scaffoldState.snackbarHostState.showSnackbar(message = "Some error occurred (${event.name})")
    }
  }

  Scaffold(
    scaffoldState = scaffoldState,
    modifier = Modifier.fillMaxSize(),
    topBar = {
      ScanAppBar(
        inProgress = state.inProgress,
        buttonType = state.toolbarButtonType,
        onRetryClick = { viewModel.handleAction(StartScanClicked) },
        onStopClick = { viewModel.handleAction(StopScanClicked) },
      )
    },
    content = { paddingValues ->
      ScanContent(
        state = state,
        onAction = viewModel::handleAction,
        modifier = Modifier.padding(paddingValues).fillMaxSize()
      )
    },
  )
}

@Composable
private fun ScanAppBar(
  inProgress: Boolean,
  buttonType: ToolbarButtonType,
  onRetryClick: () -> Unit,
  onStopClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  LoadingTopBar(
    isProgressVisible = inProgress,
    title = { Text("Scanning") },
    actions = {
      Crossfade(buttonType) { buttonType ->
        when (buttonType) {
          Retry -> RetryButton(onRetryClick)
          Stop -> StopButton(onStopClick)
          None -> Unit
        }
      }
    },
    modifier = modifier,
  )
}

@Composable
private fun RetryButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  IconButton(onClick, modifier) {
    Icon(
      painter = rememberVectorPainter(Icons.Default.Refresh),
      contentDescription = null,
    )
  }
}

@Composable
private fun StopButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  IconButton(onClick, modifier) {
    Icon(
      painter = rememberVectorPainter(Icons.Default.ExitToApp),
      contentDescription = null,
    )
  }
}

@Composable
private fun ScanContent(
  state: ScanState,
  onAction: (ScanAction) -> Unit,
  modifier: Modifier = Modifier,
) {
  when (state) {
    is Initial -> InitialContent(onAction, modifier)
    is Results -> ResultsContent(state.inProgress, state.items, onAction, modifier)
  }
}

@Composable
private fun InitialContent(
  onAction: (ScanAction) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Button(onClick = { onAction(StartScanClicked) }) {
      Text(text = "Start scanning")
    }
  }
}

@Composable
private fun ResultsContent(
  inProgress: Boolean,
  items: List<DeviceItem>,
  onAction: (ScanAction) -> Unit,
  modifier: Modifier = Modifier,
) {
  if (items.isEmpty()) {
    EmptyContent(inProgress, onRetryClick = { onAction(StartScanClicked) }, modifier)
  } else {
    DeviceItemsList(items, onAction, modifier)
  }
}

@Composable
private fun DeviceItemsList(items: List<DeviceItem>, onAction: (ScanAction) -> Unit, modifier: Modifier = Modifier) {
  LazyColumn(
    modifier,
    contentPadding = PaddingValues(
      start = 8.dp,
      end = 8.dp,
      top = 8.dp,
      bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 8.dp
    ),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    items(items, key = { it.address }) { item ->
      DeviceItem(item, onClick = { address -> onAction(DeviceItemClicked(address)) })
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DeviceItem(item: DeviceItem, onClick: (String) -> Unit, modifier: Modifier = Modifier) {
  Card(onClick = { onClick(item.address) }, modifier, ) {
    Column(Modifier.fillMaxSize()) {
      Text(item.address, Modifier.padding(all = 8.dp))
      Text(item.name, Modifier.padding(horizontal = 8.dp).padding(bottom = 8.dp))
    }
  }
}

@Composable
private fun EmptyContent(inProgress: Boolean, onRetryClick: () -> Unit, modifier: Modifier = Modifier) {
  Column(modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    if (inProgress) {
      CircularProgressIndicator()
    } else {
      Text("No devices were found during scan")
      Button(onRetryClick) {
        Text("Try again")
      }
    }
  }
}
