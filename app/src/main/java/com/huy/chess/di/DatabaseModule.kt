package com.huy.chess.di

import android.content.Context
import androidx.room.Room
import com.huy.chess.data.database.AppDatabase
import com.huy.chess.data.database.daos.HistoryDao
import com.huy.chess.data.preferences.abstraction.DataStoreRepository
import com.huy.chess.data.preferences.implement.DataStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ): DataStoreRepository = DataStoreRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"app_db").build()
    }

    @Provides
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao{
        return appDatabase.historyDao()
    }
}