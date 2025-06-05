package com.huy.chess.di

import com.huy.chess.data.network.TokenAuthenticator
import com.huy.chess.data.network.api.AuthApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.huy.chess.BuildConfig
import com.huy.chess.data.network.AuthInterceptor
import com.huy.chess.data.network.api.PuzzleApi
import com.huy.chess.data.network.api.UserApi
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providePuzzleApi(retrofit: Retrofit) : PuzzleApi {
        return retrofit.create(PuzzleApi::class.java)
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit) : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ) : Retrofit {
        return Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideOKHttpClient(
//        httpLoggingInterceptor: HttpLoggingInterceptor,
//        authInterceptor: AuthInterceptor,
//        tokenAuthenticator: TokenAuthenticator
//    ) : OkHttpClient {
//        val builder = OkHttpClient.Builder()
//            .connectTimeout(5, TimeUnit.SECONDS)
//            .retryOnConnectionFailure(false)
//            .addInterceptor(authInterceptor)
//            .addInterceptor(httpLoggingInterceptor)
//            .authenticator(tokenAuthenticator)
//        return builder.build()
//    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ) : OkHttpClient {
        try {
            val trustAllCerts = arrayOf<X509TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // Do nothing, trust all clients
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // Do nothing, trust all servers
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf() // Return an empty array
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0])
            builder.hostnameVerifier { hostname, session ->
                true
            }

            return builder
                .connectTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor(authInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .authenticator(tokenAuthenticator).build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory() : MoshiConverterFactory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return MoshiConverterFactory.create(moshi)
    }
}