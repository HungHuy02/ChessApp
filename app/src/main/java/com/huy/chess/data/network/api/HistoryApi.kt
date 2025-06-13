package com.huy.chess.data.network.api

import com.huy.chess.data.model.History
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryApi {

    @GET("v1/histories")
    suspend fun getHistories(@Query("page") page: Int) : Response<List<History>>
}