package com.bimboilya.yacr.di

import com.bimboilya.common.navigation.core.AppRouter
import com.bimboilya.yacr.navigation.AppRouterImpl
import com.bimboilya.yacr.navigation.NavCommandDispatcher
import com.bimboilya.yacr.navigation.NavCommandDispatcherImpl
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