package com.huy.chess.data.network

import android.content.Context
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.di.IoDispatcher
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreService: DataStoreService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking(dispatcher) {
            val input = dataStoreService.getAccessToken()
            Utils.decodeAESCBC(input, Constants.ACCESS_TOKEN_ALIAS)
        }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }

}