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
    suspend fun getAccessToken() : ByteArray = withContext(dispatcher) {
        dataStoreRepository.getByteArray(ACCESS_TOKEN) ?: byteArrayOf()
    }

    suspend fun setAccessToken(token: ByteArray) = withContext(dispatcher) {
        dataStoreRepository.putByteArray(ACCESS_TOKEN, token)
    }

    suspend fun getRefreshToken() : ByteArray = withContext(dispatcher) {
        dataStoreRepository.getByteArray(REFRESH_TOKEN) ?: byteArrayOf()
    }

    suspend fun setRefreshToken(token: ByteArray) = withContext(dispatcher) {
        dataStoreRepository.putByteArray(REFRESH_TOKEN, token)
    }
}
