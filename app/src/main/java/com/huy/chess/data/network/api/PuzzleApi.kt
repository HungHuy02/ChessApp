package com.huy.chess.data.network.api

import com.huy.chess.data.model.DailyPuzzle
import com.huy.chess.data.model.Puzzle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PuzzleApi {

    @GET("v1/daily_puzzles/{date}")
    suspend fun getDailyPuzzle(@Path("date") date: String): Response<DailyPuzzle>

    @GET("vv1/puzzles")
    suspend fun getPuzzle(@Query("page") elo: Int, @Query("themes") themes: String?) : Response<Puzzle>
}