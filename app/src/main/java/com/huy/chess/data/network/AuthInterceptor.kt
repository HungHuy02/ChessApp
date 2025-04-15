package com.huy.chess.data.network

import android.util.Log
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.di.IoDispatcher
import com.huy.chess.di.NoAuth
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreService: DataStoreService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val hasNoAuth = request.tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(NoAuth::class.java) != null

        if (hasNoAuth) {
            return chain.proceed(request)
        }
        val token = runBlocking(dispatcher) {
            val input = dataStoreService.getAccessToken()
            Utils.decodeAESCBC(input, Constants.ACCESS_TOKEN_ALIAS)
        }
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

}