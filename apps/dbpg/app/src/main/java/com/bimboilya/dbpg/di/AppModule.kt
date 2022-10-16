package com.bimboilya.dbpg.di

import android.content.Context
import androidx.room.Room
import com.bimboilya.dbpg.data.db.SubDao
import com.bimboilya.dbpg.data.db.SubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSubDatabase(@ApplicationContext context: Context): SubDatabase =
        Room.databaseBuilder(context, SubDatabase::class.java, "subscriptions_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSubDao(db: SubDatabase): SubDao = db.subDao()
}
