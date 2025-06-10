package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.model.Move
import com.huy.chess.data.model.OnlinePlayer
import com.huy.chess.data.network.socket.GameSocket
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.playonline.PlayOnlineAction
import com.huy.chess.ui.playonline.PlayOnlineEffect
import com.huy.chess.ui.playonline.PlayOnlineState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.utils.increment
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class PlayOnlineViewModel @Inject constructor(
    private val socket: GameSocket,
    @ApplicationContext private val context: Context,
) :
    BaseViewModel<PlayOnlineState, PlayOnlineAction, PlayOnlineEffect>(PlayOnlineState()) {

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        viewModelScope.launch {
            parseFen(Constants.START_FEN)
            socket.ready()
            socket.onGameStart().collect { json ->
                val playerAdapter = moshi.adapter(OnlinePlayer::class.java)

                val root = JSONObject(json)

                val side = root.getBoolean("side")
                val player1Json = root.getJSONObject("player1").toString()
                val player2Json = root.getJSONObject("player2").toString()

                val player1 = playerAdapter.fromJson(player1Json)
                val player2 = playerAdapter.fromJson(player2Json)
                if (player1?.id == context.userDataStore.data.first().id) {
                    updateState {
                        it.copy(
                            side = side,
                            player1 = player1!!,
                            player2 = player2!!
                        )
                    }
                } else {
                    updateState {
                        it.copy(
                            side = !side,
                            player1 = player2!!,
                            player2 = player1!!
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            socket.onMove().collect {json ->
                val moveAdapter = moshi.adapter(Move::class.java)
                val root = JSONObject(json).toString()
                val move = moveAdapter.fromJson(root)
                if(move!!.sourcePiece.toChar().isUpperCase() != state.value.side)
                    updateState { it.copy(nextMove = move) }
            }
        }
    }

    override fun processAction(action: PlayOnlineAction) {
        when(action) {
            PlayOnlineAction.ClickedAdd -> {}
            PlayOnlineAction.ClickedBack -> {}
            PlayOnlineAction.ClickedForward -> {}
            PlayOnlineAction.ClickedMore -> sendEffect(PlayOnlineEffect.ShowPlayOptionsDialog)
            PlayOnlineAction.ClickedBackButton -> sendEffect(PlayOnlineEffect.PopBackStack)
            is PlayOnlineAction.PieceCaptured -> {
                updateState {
                    val map = state.value.capturedPiece
                    map.increment(action.piece)
                    it.copy(capturedPiece = map)
                }
            }
            is PlayOnlineAction.OnMove -> {
                action.moveMaterial?.let {
                    socket.move(it)
                }
                updateState {
                    val notation = state.value.notationList.toMutableList()
                    notation.add(action.move)
                    it.copy(notationList = notation, nextMove = null)
                }

            }
            is PlayOnlineAction.Result -> {
                val gameResult = when(action.result) {
                    2 -> if(action.whiteSide) GameResult.WIN_CHECKMATE else GameResult.LOSE_CHECKMATE
                    3 -> GameResult.DRAW_STALEMATE
                    4 -> GameResult.DRAW_THREEFOLD_REPETITION
                    5 -> GameResult.DRAW_FIFTY_MOVE_RULE
                    else -> GameResult.DRAW_INSUFFICIENT_MATERIAL
                }
                sendEffect(PlayOnlineEffect.ShowEndGameDialog(gameResult))
            }
        }
    }
}