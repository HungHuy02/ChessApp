package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.setupbot.SetupBotAction
import com.huy.chess.ui.setupbot.SetupBotEffect
import com.huy.chess.ui.setupbot.SetupBotState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupBotViewModel @Inject constructor() : BaseViewModel<SetupBotState, SetupBotAction, SetupBotEffect>(
    SetupBotState()
){
    override fun processAction(action: SetupBotAction) {
        when(action) {
            SetupBotAction.ClickedBack -> sendEffect(SetupBotEffect.PopBackStack)
            is SetupBotAction.ClickedButton -> updateState { it.copy(selectedTime = action.timeType) }
            SetupBotAction.ClickShowMore -> updateState { it.copy(showTimeControl = !it.showTimeControl) }
        }
    }
}