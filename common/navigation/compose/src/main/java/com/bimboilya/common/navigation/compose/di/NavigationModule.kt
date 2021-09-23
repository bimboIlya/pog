package com.bimboilya.common.navigation.compose.di

import com.bimboilya.common.navigation.compose.AppRouterImpl
import com.bimboilya.common.navigation.compose.NavCommandDispatcher
import com.bimboilya.common.navigation.compose.NavCommandDispatcherImpl
import com.bimboilya.common.navigation.core.AppRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    @Singleton
    fun bindNavCommandDispatcher(navigatorImpl: NavCommandDispatcherImpl): NavCommandDispatcher

    @Binds
    @Singleton
    fun bindAppRouter(appRouter: AppRouterImpl): AppRouter
}