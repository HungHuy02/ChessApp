package com.huy.chess.data.network.repository

import android.content.Context
import android.net.Uri
import com.huy.chess.base.BaseRepository
import com.huy.chess.base.BaseResponse
import com.huy.chess.data.network.api.AuthApi
import com.huy.chess.data.model.request.LoginRequest
import com.huy.chess.data.model.request.LogoutRequest
import com.huy.chess.data.model.request.RefreshRequest
import com.huy.chess.data.model.request.RegisterRequest
import com.huy.chess.data.model.response.LoginResponse
import com.huy.chess.data.model.response.LogoutResponse
import com.huy.chess.data.model.response.RefreshResponse
import com.huy.chess.data.model.response.VerifyEmailResponse
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.utils.toMultipart
import com.huy.chess.utils.toRequestBody
import com.huy.chess.utils.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreService: DataStoreService,
    private val authApi: AuthApi
) : BaseRepository() {
    suspend fun register(request: RegisterRequest): Result<BaseResponse<Any>> {
        val namePart = request.name.toRequestBody()
        val emailPart = request.email.toRequestBody()
        val passwordPart = request.password.toRequestBody()
        val avatar = request.avatar?.let { context.uriToFile(Uri.parse(it))?.toMultipart("avatar") }
        return safeApiCall {authApi.register(namePart, emailPart, passwordPart, avatar) }
    }

    suspend fun socialLogin(idToken: String): Result<LoginResponse> {
        return safeApiCall { authApi.socialLogin(idToken) }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return safeApiCall { authApi.login(loginRequest) }
    }

    suspend fun refresh(token: String): Result<RefreshResponse> {
        return safeApiCall { authApi.refresh("Bearer $token", RefreshRequest(dataStoreService.getUUID())) }
    }

    suspend fun verifyEmail(email: String) : Result<VerifyEmailResponse> {
        return safeApiCall { authApi.verifyEmail(email) }
    }

    suspend fun logout() : Result<LogoutResponse> {
        return safeApiCall { authApi.logout(LogoutRequest(dataStoreService.getUUID())) }
    }
}