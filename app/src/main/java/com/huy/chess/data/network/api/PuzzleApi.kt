package com.huy.chess.data.network.api

import com.huy.chess.data.model.DailyPuzzle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PuzzleApi {

    @GET("v1/daily_puzzles/{date}")
    suspend fun getDailyPuzzle(@Path("date") date: String): Response<DailyPuzzle>
}