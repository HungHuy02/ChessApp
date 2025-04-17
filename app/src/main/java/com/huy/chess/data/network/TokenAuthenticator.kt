package com.huy.chess.data.network

import com.huy.chess.data.network.repository.AuthRepository
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.di.IoDispatcher
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import dagger.Lazy
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
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val newAccessToken: String = runBlocking(dispatcher) {
            val refreshToken = dataStoreService.getRefreshToken()
            if(refreshToken.isNotEmpty())
                authRepository.get().refresh(Utils.decodeAESCBC(refreshToken, Constants.REFRESH_TOKEN_ALIAS))
                    .onSuccess {
                        dataStoreService.setAccessToken(Utils.encodeAESCBC(it.accessToken, Constants.ACCESS_TOKEN_ALIAS))
                        return@runBlocking it.accessToken
                    }
                    .onFailure { return@runBlocking "" }
            ""
        }
        if(newAccessToken.isEmpty()) return null
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }
}