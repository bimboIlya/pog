package com.bimboilya.bluepoop.di

import com.bimboilya.bluepoop.shared.bluetooth.data.BluetoothStateRepositoryImpl
import com.bimboilya.bluepoop.shared.bluetooth.domain.BluetoothStateRepository
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
  fun bluetoothStateRepository(impl: BluetoothStateRepositoryImpl): BluetoothStateRepository

  companion object {

    @Provides
    @Singleton
    fun coroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)
  }
}
