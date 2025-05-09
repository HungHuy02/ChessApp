package com.huy.chess.base

import android.util.Log
import retrofit2.Response

open class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Empty body"))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                val exception = if(response.code() == 403) ApiException.Forbidden("Forbidden: $errorBody")
                else Exception("Error: ${response.code()} - $errorBody")
                return Result.failure(exception)
            }
        } catch (e: Exception) {
            Log.e("ApiError", "API call failed: ${e.message}", e)
            Result.failure(e)
        }
    }
}

sealed class ApiException(message: String) : Exception(message) {
    class AccountExists(message: String) : ApiException(message)
    class Unauthorized(message: String) : ApiException(message)
    class Forbidden(message: String) : ApiException(message)
    class NotFound(message: String) : ApiException(message)
    class ServerError(message: String) : ApiException(message)
    class Unknown(message: String) : ApiException(message)
}