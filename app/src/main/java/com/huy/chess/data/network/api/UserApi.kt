package com.huy.chess.data.network.api

import com.huy.chess.data.model.response.GetDetailsResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("v1/users")
    suspend fun getDetails() : Response<GetDetailsResponse>
}