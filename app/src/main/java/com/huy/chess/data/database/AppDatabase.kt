package com.huy.chess.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huy.chess.data.database.daos.HistoryDao
import com.huy.chess.data.database.entities.HistoryEntity

@Database(entities = [HistoryEntity::class],version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun historyDao(): HistoryDao
}