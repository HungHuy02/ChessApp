package com.huy.chess.data.network.repository

import com.huy.chess.base.BaseRepository
import com.huy.chess.data.network.api.PuzzleApi
import javax.inject.Inject

class PuzzleRepository @Inject constructor(
    private val puzzleApi: PuzzleApi
) : BaseRepository() {

    suspend fun getDailyPuzzle(date: String) = safeApiCall { puzzleApi.getDailyPuzzle(date) }

    suspend fun getPuzzle(elo: Int, themes: String? = null) = safeApiCall { puzzleApi.getPuzzle(elo, themes) }
}