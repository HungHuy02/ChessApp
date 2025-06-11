package com.huy.chess.data.network

import android.content.Context
import com.huy.chess.data.database.repositories.LocalHistoryRepository
import com.huy.chess.data.network.repository.AuthRepository
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.di.IoDispatcher
import com.huy.chess.proto.User
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val dataStoreService: DataStoreService,
    private val authRepository: Lazy<AuthRepository>,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val localHistoryRepository: LocalHistoryRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.encodedPath.endsWith("token")) {
            runBlocking(dispatcher) {
                dataStoreService.setRefreshToken(byteArrayOf())
            }
            return null
        }
        val newAccessToken: String? = runBlocking(dispatcher) {
            val refreshToken = dataStoreService.getRefreshToken()
            if(refreshToken.isNotEmpty())
                authRepository.get().refresh(Utils.decodeAESCBC(refreshToken, Constants.REFRESH_TOKEN_ALIAS))
                    .onSuccess {
                        dataStoreService.setAccessToken(Utils.encodeAESCBC(it.accessToken, Constants.ACCESS_TOKEN_ALIAS))
                        return@runBlocking it.accessToken
                    }
                    .onFailure {
                        dataStoreService.setAccessToken("".toByteArray())
                        dataStoreService.setRefreshToken("".toByteArray())
                        context.userDataStore.updateData { User.newBuilder().setIsLogin(false).build() }
                        localHistoryRepository.clearAll()
                        return@runBlocking null
                    }
            null
        }
        if(newAccessToken.isNullOrEmpty()) return null
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }
}