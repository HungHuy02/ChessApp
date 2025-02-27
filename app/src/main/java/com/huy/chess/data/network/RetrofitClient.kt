package com.huy.chess.data.network

import android.content.Context
import android.util.Log
import com.huy.chess.utils.Constants
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private lateinit var retrofit: Retrofit

    fun getRetrofit(): Retrofit {
        if (!::retrofit.isInitialized) {
            throw IllegalStateException("RetrofitClient is not initialized. Call createClient() first.")
        }
        return retrofit
    }

    fun createClient(context: Context) {
        val httpClient = setupOkHttpClient(context)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
    }

    private fun setupOkHttpClient(context: Context): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
//        httpClient.authenticator(TokenAuthenticator(context))

        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val originalRequest: Request = chain.request()
            val builder: Request.Builder = originalRequest.newBuilder()
//                .header("Authorization", "Bearer " + StorageUtil(context).accessToken)
//            Log.e("test", StorageUtil(context).accessToken)
            val newRequest: Request = builder.build()
            chain.proceed(newRequest)
        })
        return httpClient.build()
    }
}