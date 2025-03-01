package com.huy.chess.data.network

import com.huy.chess.data.service.DataStoreService
import com.huy.chess.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val dataStoreService: DataStoreService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Authenticator {

    private val authScope = CoroutineScope(SupervisorJob() + dispatcher)
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        var newAccessToken: String? = null

        authScope.launch {
            mutex.withLock {
                newAccessToken = APIManager.refreshToken()
                newAccessToken?.let { dataStoreService.setAccessToken(it) }
            }
        }

        return newAccessToken?.let {
            response.request.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
    }
}