package com.huy.chess.data.network.repository

import android.content.Context
import android.net.Uri
import com.huy.chess.base.BaseRepository
import com.huy.chess.data.model.User
import com.huy.chess.data.model.response.GetDetailsResponse
import com.huy.chess.data.network.api.UserApi
import com.huy.chess.utils.toMultipart
import com.huy.chess.utils.toRequestBody
import com.huy.chess.utils.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userApi: UserApi
) : BaseRepository() {

    suspend fun getDetails(): Result<GetDetailsResponse> {
        return safeApiCall { userApi.getDetails() }
    }

    suspend fun updateUser(user: User): Result<User> {
        val namePart = user?.name?.toRequestBody()
        val avatar = user?.avatar?.let { context.uriToFile(Uri.parse(it))?.toMultipart("avatar") }
        return safeApiCall { userApi.updateUser(namePart, avatar) }
    }
}