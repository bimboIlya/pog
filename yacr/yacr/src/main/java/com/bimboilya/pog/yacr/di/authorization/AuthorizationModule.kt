package com.bimboilya.pog.yacr.di.authorization

import com.bimboilya.pog.yacr.navigation.screens.authorization.AuthorizationRouterImpl
import com.bimboilya.yacr.feature.authorization.presentation.AuthorizationRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthorizationModule {

    @Binds
    fun bindRouter(router: AuthorizationRouterImpl): AuthorizationRouter
}