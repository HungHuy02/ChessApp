package com.huy.chess.data.network.repository

import com.huy.chess.data.network.api.AuthApi
import com.huy.chess.base.BaseResponse
import com.huy.chess.model.request.RegisterRequest
import com.huy.chess.utils.toMultipart
import com.huy.chess.utils.toRequestBody
import java.io.File
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
){
    suspend fun register(request: RegisterRequest) : Result<BaseResponse> {
        return try {
            val namePart = request.name.toRequestBody()
            val emailPart = request.email.toRequestBody()
            val passwordPart = request.password.toRequestBody()
            val avatar = File(request.avatar).toMultipart("avatar")
            val response = authApi.register(namePart, emailPart, passwordPart, avatar)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Empty body"))
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}