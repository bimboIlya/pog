package com.bimboilya.common.error.di

import com.bimboilya.common.error.ErrorDelegate
import com.bimboilya.common.error.ErrorDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorHandlingModule {

    @Binds
    fun bindErrorDelegate(delegate: ErrorDelegateImpl): ErrorDelegate
}