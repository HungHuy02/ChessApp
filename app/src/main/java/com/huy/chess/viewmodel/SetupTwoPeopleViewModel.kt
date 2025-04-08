package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleAction
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleEffect
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupTwoPeopleViewModel @Inject constructor() : BaseViewModel<SetupTwoPeopleState, SetupTwoPeopleAction, SetupTwoPeopleEffect>(
    SetupTwoPeopleState()
){
    override fun processAction(action: SetupTwoPeopleAction) {
        when(action) {
            SetupTwoPeopleAction.ClickedBack -> sendEffect(SetupTwoPeopleEffect.PopBackStack)
            is SetupTwoPeopleAction.ClickedButton -> updateState { it.copy(selectedTime = action.timeType) }
            SetupTwoPeopleAction.ClickShowMore -> updateState { it.copy(showTimeControl = !it.showTimeControl) }
        }
    }
}