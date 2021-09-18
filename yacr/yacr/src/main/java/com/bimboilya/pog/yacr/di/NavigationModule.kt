package com.bimboilya.pog.yacr.di

import com.bimboilya.common.navigation.AppRouter
import com.bimboilya.pog.yacr.navigation.AppRouterImpl
import com.bimboilya.pog.yacr.navigation.NavCommandDispatcher
import com.bimboilya.pog.yacr.navigation.NavCommandDispatcherImpl
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