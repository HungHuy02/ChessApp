package com.huy.chess.data.network.api

import com.huy.chess.data.model.User
import com.huy.chess.data.model.response.GetDetailsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface UserApi {

    @GET("v1/users")
    suspend fun getDetails() : Response<User>

    @Multipart
    @PUT("v1/users")
    suspend fun updateUser(
        @Part("name") name: RequestBody?,
        @Part avatar: MultipartBody.Part?
    ) : Response<User>
}