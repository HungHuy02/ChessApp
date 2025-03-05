package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.base.NoEffect
import com.huy.chess.ui.changetime.ChangeTimeAction
import com.huy.chess.ui.changetime.ChangeTimeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeTimeViewModel @Inject constructor() :
    BaseViewModel<ChangeTimeState, ChangeTimeAction, NoEffect>(ChangeTimeState()) {

    override fun processAction(action: ChangeTimeAction) {
        when (action) {
            ChangeTimeAction.ToggleShowMore -> updateState { it.copy(isMoreSetting = !it.isMoreSetting) }
            is ChangeTimeAction.ClickedButton -> updateState { it.copy(selectedTime = action.selectedTime) }
        }
    }
}