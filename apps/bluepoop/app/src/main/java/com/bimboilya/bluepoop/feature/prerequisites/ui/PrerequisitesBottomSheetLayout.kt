package com.bimboilya.bluepoop.feature.prerequisites.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberPrerequisitesBottomSheetState(): ModalBottomSheetState =
  rememberModalBottomSheetState(
    initialValue = ModalBottomSheetValue.Hidden,
    confirmValueChange = { false },
    skipHalfExpanded = true,
  )

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrerequisitesBottomSheetLayout(
  sheetState: ModalBottomSheetState,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  ModalBottomSheetLayout(
    sheetContent = { PrerequisitesBottomSheet() },
    modifier = modifier,
    sheetState = sheetState,
    sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    scrimColor = Color.Black.copy(alpha = 0.32f),
    content = content,
  )
}


