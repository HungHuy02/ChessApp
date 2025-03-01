package com.huy.chess.data.service

import com.huy.chess.data.preferences.abstraction.DataStoreRepository
import com.huy.chess.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val ACCESS_TOKEN = "access_token"
private const val REFRESH_TOKEN = "refresh_token"

class DataStoreService @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getAccessToken() : String = withContext(dispatcher) {
        dataStoreRepository.getString(ACCESS_TOKEN) ?: ""
    }

    suspend fun setAccessToken(token: String) = withContext(dispatcher) {
        dataStoreRepository.putString(ACCESS_TOKEN, token)
    }

    suspend fun getRefreshToken() : String = withContext(dispatcher) {
        dataStoreRepository.getString(REFRESH_TOKEN) ?: ""
    }

    suspend fun setRefreshToken(token: String) = withContext(dispatcher) {
        dataStoreRepository.putString(REFRESH_TOKEN, token)
    }
}