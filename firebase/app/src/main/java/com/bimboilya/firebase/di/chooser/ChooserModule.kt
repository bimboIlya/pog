package com.bimboilya.firebase.di.chooser

import com.bimboilya.firebase.feature.chooser.presentation.ChooserRouter
import com.bimboilya.firebase.navigation.chooser.ChooserRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ChooserModule {

    @Binds
    @ViewModelScoped
    fun bindRouter(router: ChooserRouterImpl): ChooserRouter
}