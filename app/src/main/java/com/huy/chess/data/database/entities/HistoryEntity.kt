package com.huy.chess.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "histories")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "ids")
    val ids: String,
    @ColumnInfo(name = "white_player")
    val white: String,
    @ColumnInfo(name = "black_player")
    val black: String,
    @ColumnInfo(name = "result")
    val result: String,
    @ColumnInfo(name = "white_elo")
    val whiteElo: Int,
    @ColumnInfo(name = "black_elo")
    val blackElo: Int,
    @ColumnInfo(name = "white_rating_diff")
    val whiteRatingDiff: String?,
    @ColumnInfo(name = "black_rating_diff")
    val blackRatingDiff: String?,
    val timeControl: String,
    @ColumnInfo(name = "notation")
    val notation: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long?,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,
)