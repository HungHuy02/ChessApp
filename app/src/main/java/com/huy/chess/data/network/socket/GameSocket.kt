package com.huy.chess.data.network.socket

import android.util.Log
import com.huy.chess.data.model.MatchRequest
import com.huy.chess.data.model.Move
import com.huy.chess.utils.toJsonObject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.JsonObject
import java.net.URISyntaxException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSocket @Inject constructor() {

    companion object {
        const val TAG = "GameSocket"
        const val REQUEST_TO_PLAY =  "request_to_play"
        const val MOVE = "move"
        const val WANT_TO_DRAW = "want_to_draw"
        const val RESULT = "result"
    }

    private lateinit var socket: Socket

    fun connect() {
        try {
            socket = IO.socket("")
            socket.connect()
        } catch (e: URISyntaxException) {
            Log.e(TAG, e.message.toString())
        }
    }

    fun requestToPlay(matchRequest: MatchRequest) {
        socket.emit(REQUEST_TO_PLAY, matchRequest.toJsonObject())
    }

    fun move(move: Move) {
        socket.emit(MOVE, move.toJsonObject())
    }

    fun wantToDraw() {
        socket.emit(WANT_TO_DRAW)
    }

    fun result(result: Int) {
        socket.emit(RESULT, result.toJsonObject())
    }

    fun onMove() : Flow<JsonObject> = socket.onEventFlow(MOVE)
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