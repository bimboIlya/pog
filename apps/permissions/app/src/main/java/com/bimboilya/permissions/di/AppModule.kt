package com.bimboilya.permissions.di

import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.bimboilya.common.navigation.launcher.ActivityLauncherImpl
import com.bimboilya.common.navigation.launcher.ActivityProvider
import com.bimboilya.common.permissions.PermissionManager
import com.bimboilya.common.permissions.internal.PermissionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindActivityLauncher(impl: ActivityLauncherImpl): ActivityLauncher

    @Binds
    @Singleton
    fun bindPermissionManager(impl: PermissionManagerImpl): PermissionManager

    companion object {

        @Provides
        @Singleton
        fun provideActivityProvider() = ActivityProvider()
    }
}
