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
                return Result.failure(Exception("Error: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("ApiError", "API call failed: ${e.message}", e)
            Result.failure(e)
        }
    }

}