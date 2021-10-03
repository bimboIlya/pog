package com.bimboilya.firebase.di

import android.content.Context
import com.bimboilya.common.preferences.Preferences
import com.bimboilya.common.preferences.PreferencesImpl
import com.bimboilya.common.qualifiers.AppPreferences
import com.bimboilya.common.qualifiers.AppScope
import com.bimboilya.injection.coroutines.CoroutinesModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module(includes = [CoroutinesModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @AppPreferences
    fun provideAppPreferences(
        @ApplicationContext context: Context,
        @AppScope scope: CoroutineScope,
    ): Preferences =
        PreferencesImpl("application_store", context, scope)
}