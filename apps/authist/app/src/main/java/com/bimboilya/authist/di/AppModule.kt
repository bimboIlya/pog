package com.bimboilya.authist.di

import com.bimboilya.authist.data.repository.AuthRepositoryImpl
import com.bimboilya.authist.domain.repository.AuthRepository
import com.bimboilya.common.navigation.launcher.ActivityLauncher
import com.bimboilya.common.navigation.launcher.ActivityLauncherImpl
import com.bimboilya.common.navigation.launcher.ActivityProvider
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
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindActivityLauncher(impl: ActivityLauncherImpl): ActivityLauncher

    companion object {

        @Provides
        @Singleton
        fun provideActivityProvider() = ActivityProvider()
    }
}
