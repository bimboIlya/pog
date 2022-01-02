package com.bimboilya.yacr.di.navigation

import com.bimboilya.common.navigation.jetpack.AppRouter
import com.bimboilya.common.navigation.jetpack.AppRouterImpl
import com.bimboilya.common.navigation.jetpack.NavCommandDispatcher
import com.bimboilya.common.navigation.jetpack.NavCommandDispatcherImpl
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