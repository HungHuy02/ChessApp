package com.huy.chess.data.network.api

import com.huy.chess.base.BaseResponse
import com.huy.chess.model.request.LoginRequest
import com.huy.chess.model.response.LoginResponse
import com.huy.chess.model.response.RefreshResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {

    @Multipart
    @POST("/auth/register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part avatar: MultipartBody.Part
    ) : Response<BaseResponse<Any>>

    @POST("/auth/social")
    suspend fun socialLogin(@Body idToken: String) : Response<LoginResponse>

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("/auth/refresh")
    suspend fun refresh() : Response<RefreshResponse>
}