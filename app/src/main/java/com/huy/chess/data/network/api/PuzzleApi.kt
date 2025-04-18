package com.huy.chess.data.network.api

import com.huy.chess.data.model.DailyPuzzle
import retrofit2.Response
import retrofit2.http.GET

interface PuzzleApi {

    @GET("v1/daily_puzzle")
    suspend fun getDailyPuzzle(): Response<DailyPuzzle>
}