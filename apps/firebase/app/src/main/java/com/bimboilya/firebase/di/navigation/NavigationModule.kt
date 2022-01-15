package com.bimboilya.firebase.di.navigation

import com.bimboilya.common.navigation.voyager.AppRouter
import com.bimboilya.common.navigation.voyager.AppRouterImpl
import com.bimboilya.common.navigation.voyager.CommandDispatcher
import com.bimboilya.common.navigation.voyager.CommandDispatcherImpl
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
    fun bindCommandDispatcher(dispatcher: CommandDispatcherImpl): CommandDispatcher

    @Binds
    @Singleton
    fun bindAppRouter(appRouter: AppRouterImpl): AppRouter
}