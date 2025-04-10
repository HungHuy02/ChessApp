package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.selectdate.SelectDateAction
import com.huy.chess.ui.selectdate.SelectDateEffect
import com.huy.chess.ui.selectdate.SelectDateState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectDateViewModel @Inject constructor() :
    BaseViewModel<SelectDateState, SelectDateAction, SelectDateEffect>(SelectDateState.Default) {
    override fun processAction(action: SelectDateAction) {
        when(action) {
            SelectDateAction.ClickedDate -> {}
        }
    }
}