package com.bimboilya.firebase.di.crash

import com.bimboilya.feature.crashlytics.main.presentation.CrashRouter
import com.bimboilya.firebase.navigation.crashlytics.CrashRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface CrashModule {

    @Binds
    @ViewModelScoped
    fun bindRouter(router: CrashRouterImpl): CrashRouter
}