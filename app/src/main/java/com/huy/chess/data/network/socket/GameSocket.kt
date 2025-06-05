package com.huy.chess.data.network.socket

import android.util.Log
import com.huy.chess.BuildConfig
import com.huy.chess.data.model.MatchRequest
import com.huy.chess.data.model.Move
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.di.IoDispatcher
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import com.huy.chess.utils.toJsonObject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import okhttp3.OkHttpClient
import java.net.URISyntaxException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Singleton
class GameSocket @Inject constructor(
    private val dataStoreService: DataStoreService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    companion object {
        const val TAG = "GameSocket"
        const val REQUEST_TO_PLAY =  "request_to_play"
        const val MATCH_SUCCESSFUL =  "match_successful"
        const val READY = "ready"
        const val GAME_START = "game_start"
        const val MOVE = "move"
        const val WANT_TO_DRAW = "want_to_draw"
        const val ACCEPT_DRAW = "accept_draw"
        const val RESULT = "result"
    }

    private lateinit var socket: Socket

    fun connect() {
        CoroutineScope(dispatcher).launch {
            try {
                val headers = mapOf(
                    "Authorization" to listOf("Bearer ${Utils.decodeAESCBC(dataStoreService.getAccessToken(), Constants.ACCESS_TOKEN_ALIAS)}"),
                )
                val client = getUnsafeOkHttpClient()
                val opts = IO.Options().apply {
                    callFactory = client
                    webSocketFactory = client
                    extraHeaders = headers
                }
                socket = IO.socket("https://192.168.1.8:8017", opts)
                socket.connect()
                socket.on(Socket.EVENT_CONNECT_ERROR) {
                    Log.e("Socket", "Connect error", it[0] as Exception)
                }
            } catch (e: URISyntaxException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    fun requestToPlay(matchRequest: MatchRequest) {
        socket.emit(REQUEST_TO_PLAY, matchRequest.toJsonObject())
    }

    fun ready() {
        socket.emit(READY)
    }

    fun move(move: Move) {
        socket.emit(MOVE, move.toJsonObject())
    }

    fun wantToDraw() {
        socket.emit(WANT_TO_DRAW)
    }

    fun acceptDraw() {
        socket.emit(ACCEPT_DRAW)
    }

    fun result(result: Int) {
        socket.emit(RESULT, result.toJsonObject())
    }

    fun onMatchSuccessful() : Flow<JsonObject> = socket.onEventFlow(MATCH_SUCCESSFUL)
    fun onMove() : Flow<JsonObject> = socket.onEventFlow(MOVE)
    fun onGameStart() : Flow<JsonObject> = socket.onEventFlow(GAME_START)
    fun onWantToDraw() : Flow<JsonObject> = socket.onEventFlow(WANT_TO_DRAW)
    fun onResult() : Flow<JsonObject> = socket.onEventFlow(RESULT)
}

fun Socket.onEventFlow(eventName: String) : Flow<JsonObject> = callbackFlow {
    val listener = Emitter.Listener {
        trySend(it[0] as JsonObject)
    }
    this@onEventFlow.on(eventName, listener)
    awaitClose { this@onEventFlow.off(eventName, listener) }
}

fun getUnsafeOkHttpClient(): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    val sslSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()
}