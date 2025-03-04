package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.contract.ChangeTimeEvent
import com.huy.chess.contract.ChangeTimeIntent
import com.huy.chess.contract.ChangeTimeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeTimeViewModel @Inject constructor() :
    BaseViewModel<ChangeTimeState, ChangeTimeIntent, ChangeTimeEvent>(ChangeTimeState()) {

    override fun processIntent(intent: ChangeTimeIntent) {
        when (intent) {
            ChangeTimeIntent.ToggleShowMore -> updateState { it.copy(isMoreSetting = !it.isMoreSetting) }
            is ChangeTimeIntent.ClickedButton -> updateState { it.copy(selectedTime = intent.selectedTime) }
        }
    }
}