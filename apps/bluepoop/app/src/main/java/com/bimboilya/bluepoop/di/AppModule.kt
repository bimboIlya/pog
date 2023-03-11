package com.bimboilya.bluepoop.di

import com.bimboilya.bluepoop.bluetooth.BluetoothStateObserver
import com.bimboilya.bluepoop.bluetooth.BluetoothStateObserverImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindBluetoothStateObserver(impl: BluetoothStateObserverImpl): BluetoothStateObserver

    companion object {

        @Provides
        @Singleton
        fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
