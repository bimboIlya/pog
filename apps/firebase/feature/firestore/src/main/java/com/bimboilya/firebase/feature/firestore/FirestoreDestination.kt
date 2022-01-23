package com.bimboilya.firebase.feature.firestore

import cafe.adriel.voyager.core.screen.Screen
import com.bimboilya.common.navigation.voyager.ScreenDestination
import com.bimboilya.firebase.feature.firestore.ui.FirestoreScreen

class FirestoreDestination : ScreenDestination {

    override fun createScreen(): Screen =
        FirestoreScreen()
}