package com.huy.chess.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.model.MatchRequest
import com.huy.chess.data.network.socket.GameSocket
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.onlinewaiting.OnlineWaitingAction
import com.huy.chess.ui.onlinewaiting.OnlineWaitingEffect
import com.huy.chess.ui.onlinewaiting.OnlineWaitingState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineWaitingViewModel @Inject constructor(
    private val gameSocket: GameSocket,
    @ApplicationContext private val context: Context
) : BaseViewModel<OnlineWaitingState, OnlineWaitingAction, OnlineWaitingEffect>(OnlineWaitingState()) {

    init {
        viewModelScope.launch {
            val user = context.userDataStore.data.first()
            gameSocket.requestToPlay(
                MatchRequest(user.id, user.elo, 8)
            )
            gameSocket.onMatchSuccessful().collect {
                Log.e("tag", it.toString())
                sendEffect(OnlineWaitingEffect.NavigatePlayOnline)
            }
        }
    }

    override fun processAction(action: OnlineWaitingAction) {
        when(action) {
            OnlineWaitingAction.ClickedBack -> sendEffect(OnlineWaitingEffect.PopBackStack)
            OnlineWaitingAction.ClickedCancel -> sendEffect(OnlineWaitingEffect.PopBackStack)
        }
    }

}