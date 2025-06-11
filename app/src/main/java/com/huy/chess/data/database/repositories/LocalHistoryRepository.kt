package com.huy.chess.data.database.repositories

import com.huy.chess.data.database.daos.HistoryDao
import com.huy.chess.data.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalHistoryRepository @Inject constructor(private val historyDao: HistoryDao) {

    private val PAGE_SIZE = 10

    fun getAllHistories(): Flow<List<HistoryEntity>> {
        return historyDao.getAll()
    }

    fun getHistoryById(id: Long): Flow<HistoryEntity?> {
        return historyDao.getHistoryById(id)
    }

    fun getHistoriesForPage(page: Int): Flow<List<HistoryEntity>> {
        val offset = (page - 1) * PAGE_SIZE
        return historyDao.getHistoriesPaged(PAGE_SIZE, offset)
    }

    suspend fun createHistory(entity: HistoryEntity): Long {
        return historyDao.createHistory(entity)
    }

    suspend fun insertAll(list: List<HistoryEntity>) {
        historyDao.insertAll(list)
    }

    suspend fun updateHistory(entity: HistoryEntity) {
        historyDao.updateHistory(entity)
    }

    suspend fun deleteHistory(entity: HistoryEntity) {
        historyDao.deleteHistory(entity)
    }

    suspend fun clearAll() = historyDao.clearAll()
}