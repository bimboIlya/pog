package com.bimboilya.bluepoop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bimboilya.bluepoop.feature.NavGraphs
import com.bimboilya.bluepoop.feature.prerequisites.ui.PrerequisitesBottomSheetLayout
import com.bimboilya.bluepoop.feature.prerequisites.ui.rememberPrerequisitesBottomSheetState
import com.bimboilya.bluepoop.shared.navigation.BluepoopNavigator
import com.bimboilya.bluepoop.shared.navigation.NavigationCommand
import com.bimboilya.common.ktx.compose.collectAsStateWithLifecycle
import com.bimboilya.common.ktx.compose.collectInComposition
import com.bimboilya.common.ui.theme.PogTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class BluepoopActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      PogTheme {
        Surface(Modifier.fillMaxSize()) {
          BluepoopApplication()
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BluepoopApplication() {
  val viewModel: BluepoopViewModel = viewModel()

  val sheetState = rememberPrerequisitesBottomSheetState()
  PrerequisitesBottomSheetLayout(sheetState, modifier = Modifier.fillMaxSize()) {
    AppNavigation(viewModel.navigationCommands)
  }

  val state by viewModel.state.collectAsStateWithLifecycle()
  LaunchedEffect(state) {
    if (state.isPrerequisitesVisible) sheetState.show() else sheetState.hide()
  }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun AppNavigation(navigationCommands: Flow<NavigationCommand>) {
  val navEngine = rememberAnimatedNavHostEngine()
  val navController = navEngine.rememberNavController()

  DestinationsNavHost(
    navGraph = NavGraphs.root,
    engine = navEngine,
    navController = navController,
    modifier = Modifier.fillMaxSize(),
  )

  val navigator = remember(navController) { BluepoopNavigator(navController) }
  navigationCommands.collectInComposition(block = navigator::executeCommand)
}
