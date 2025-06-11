package com.huy.chess.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.huy.chess.data.database.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("select * from histories")
    fun getAll(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM histories WHERE id = :id")
    fun getHistoryById(id: Long): Flow<HistoryEntity>

    @Insert
    suspend fun createHistory(entity: HistoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(histories: List<HistoryEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHistory(entity: HistoryEntity)

    @Delete
    suspend fun deleteHistory(entity: HistoryEntity)

    @Query("SELECT * FROM histories ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    fun getHistoriesPaged(limit: Int, offset: Int): Flow<List<HistoryEntity>>

    @Query("DELETE FROM histories")
    suspend fun clearAll()
}