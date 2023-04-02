package com.bimboilya.bluepoop.feature.prerequisites.presentation

sealed interface PrerequisitesAction {
    object RequestPermissionsClicked : PrerequisitesAction
    object EnableBluetoothClicked : PrerequisitesAction
    object OpenSettingsClicked : PrerequisitesAction
}
