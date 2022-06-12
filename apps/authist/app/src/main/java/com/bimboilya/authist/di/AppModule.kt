package com.bimboilya.authist.di

import com.bimboilya.authist.ActivityProvider
import com.bimboilya.authist.data.repository.AuthRepositoryImpl
import com.bimboilya.authist.domain.repository.AuthRepository
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

    companion object {

        @Provides
        fun provideActivityProvider() = ActivityProvider
    }
}
