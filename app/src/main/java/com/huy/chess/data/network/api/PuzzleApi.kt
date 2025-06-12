package com.huy.chess.data.network.api

import com.huy.chess.data.model.DailyPuzzle
import com.huy.chess.data.model.Puzzle
import com.huy.chess.data.model.request.SolvedRequest
import com.huy.chess.data.model.response.SolvedResponse
import com.huy.chess.di.NoAuth
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PuzzleApi {

    @GET("v1/daily_puzzles/{date}")
    suspend fun getDailyPuzzle(@Path("date") date: String): Response<DailyPuzzle>

    @GET("v1/puzzles")
    suspend fun getPuzzle(@Query("page") elo: Int, @Query("themes") themes: String?) : Response<Puzzle>

    @GET("v1/puzzles/no_rated")
    suspend fun getPuzzleGuest(@Query("elo") elo: Int, @Query("themes") themes: String?) : Response<Puzzle>

    @POST("v1/puzzles/solved")
    suspend fun solved(@Body solvedRequest: SolvedRequest) : Response<SolvedResponse>
}