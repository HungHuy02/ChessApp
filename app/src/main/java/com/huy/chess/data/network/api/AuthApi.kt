package com.huy.chess.data.network.api

import com.huy.chess.base.BaseResponse
import com.huy.chess.di.NoAuth
import com.huy.chess.data.model.request.LoginRequest
import com.huy.chess.data.model.request.RefreshRequest
import com.huy.chess.data.model.response.LoginResponse
import com.huy.chess.data.model.response.RefreshResponse
import com.huy.chess.data.model.response.VerifyEmailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {

    @NoAuth
    @Multipart
    @POST("v1/auth/register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part avatar: MultipartBody.Part?
    ) : Response<BaseResponse<Any>>


    @NoAuth
    @POST("v1/auth/social")
    suspend fun socialLogin(@Body idToken: String) : Response<LoginResponse>

    @NoAuth
    @POST("v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("v1/auth/refresh_token")
    suspend fun refresh(
        @Header("Authorization") refreshToken: String,
        @Body refreshRequest: RefreshRequest
    ) : Response<RefreshResponse>

    @NoAuth
    @POST("v1/auth/verify_email")
    suspend fun verifyEmail(@Body email: String) : Response<VerifyEmailResponse>
}