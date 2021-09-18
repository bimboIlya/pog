package com.bimboilya.yacr.feature.authorization.di

import com.bimboilya.yacr.feature.authorization.network.repository.AuthRepositoryImpl
import com.bimboilya.yacr.feature.authorization.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthorizationModule {

    @Binds
    @Singleton
    fun bindAuthorizationRepository(repository: AuthRepositoryImpl): AuthRepository
}
