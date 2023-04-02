package com.bimboilya.bluepoop.feature.prerequisites.presentation

import com.bimboilya.common.ktx.android.openSettings
import com.bimboilya.common.navigation.launcher.ActivityProvider
import javax.inject.Inject

class PrerequisitesRouter @Inject constructor() {

  fun openSettings() {
    ActivityProvider.activity?.openSettings()
  }
}
