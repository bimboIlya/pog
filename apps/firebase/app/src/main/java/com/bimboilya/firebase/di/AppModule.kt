package com.bimboilya.firebase.di

import android.content.Context
import com.bimboilya.common.preferences.Preferences
import com.bimboilya.common.preferences.PreferencesImpl
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
    fun provideAppPreferences(
        @ApplicationContext context: Context,
        scope: CoroutineScope,
    ): Preferences =
        PreferencesImpl("firebase_store", context, scope)
}