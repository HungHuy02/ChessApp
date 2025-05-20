package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.onlinewaiting.OnlineWaitingAction
import com.huy.chess.ui.onlinewaiting.OnlineWaitingEffect
import com.huy.chess.ui.onlinewaiting.OnlineWaitingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineWaitingViewModel @Inject constructor() : BaseViewModel<OnlineWaitingState, OnlineWaitingAction, OnlineWaitingEffect>(OnlineWaitingState()) {

    init {
        viewModelScope.launch {
            delay(5000)
            sendEffect(OnlineWaitingEffect.NavigatePlayOnline)
        }
    }

    override fun processAction(action: OnlineWaitingAction) {
        when(action) {
            OnlineWaitingAction.ClickedBack -> sendEffect(OnlineWaitingEffect.PopBackStack)
            OnlineWaitingAction.ClickedCancel -> sendEffect(OnlineWaitingEffect.PopBackStack)
        }
    }

}