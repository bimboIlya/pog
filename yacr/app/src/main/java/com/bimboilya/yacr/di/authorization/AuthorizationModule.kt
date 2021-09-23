package com.bimboilya.yacr.di.authorization

import com.bimboilya.yacr.feature.authorization.presentation.AuthorizationRouter
import com.bimboilya.yacr.navigation.screens.authorization.AuthorizationRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface AuthorizationModule {

    @Binds
    @ViewModelScoped
    fun bindRouter(router: AuthorizationRouterImpl): AuthorizationRouter
}