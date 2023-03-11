package com.bimboilya.bluepoop.di

import com.bimboilya.bluepoop.bluetooth.BluetoothStateObserver
import com.bimboilya.bluepoop.bluetooth.BluetoothStateObserverImpl
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

        @Provides
        @Singleton
        fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
