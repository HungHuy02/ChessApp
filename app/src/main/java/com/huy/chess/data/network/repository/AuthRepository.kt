package com.huy.chess.data.network.repository

import com.huy.chess.base.BaseRepository
import com.huy.chess.base.BaseResponse
import com.huy.chess.data.network.api.AuthApi
import com.huy.chess.model.request.LoginRequest
import com.huy.chess.model.request.RegisterRequest
import com.huy.chess.model.response.LoginResponse
import com.huy.chess.model.response.RefreshResponse
import com.huy.chess.utils.toMultipart
import com.huy.chess.utils.toRequestBody
import java.io.File
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : BaseRepository() {
    suspend fun register(request: RegisterRequest): Result<BaseResponse<Any>> {
        val namePart = request.name.toRequestBody()
        val emailPart = request.email.toRequestBody()
        val passwordPart = request.password.toRequestBody()
        val avatar = request.avatar?.let { File(it).toMultipart("avatar") }
        return safeApiCall {authApi.register(namePart, emailPart, passwordPart, avatar) }
    }

    suspend fun socialLogin(idToken: String): Result<LoginResponse> {
        return safeApiCall { authApi.socialLogin(idToken) }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return safeApiCall { authApi.login(loginRequest) }
    }

    suspend fun refresh(): Result<RefreshResponse> {
        return safeApiCall { authApi.refresh() }
    }
}