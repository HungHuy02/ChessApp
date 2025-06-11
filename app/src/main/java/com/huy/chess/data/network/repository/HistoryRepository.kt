package com.huy.chess.data.network.repository

import com.huy.chess.base.BaseRepository
import com.huy.chess.data.network.api.HistoryApi
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyApi: HistoryApi
) : BaseRepository() {

    suspend fun getHistories(page: Int) = safeApiCall { historyApi.getHistories(page) }
}