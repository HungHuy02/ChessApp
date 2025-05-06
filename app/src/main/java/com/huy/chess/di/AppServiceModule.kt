package com.huy.chess.di

import android.content.Context
import com.huy.chess.data.service.StockfishService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object AppServiceModule {

    @Provides
    fun provideStockfishService(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): StockfishService {
        return StockfishService(context, dispatcher)
    }
}