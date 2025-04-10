package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.base.NoEffect
import com.huy.chess.ui.MainAction
import com.huy.chess.ui.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainAction, NoEffect>(MainState()) {
    override fun processAction(action: MainAction) {
        when(action) {
            MainAction.UpdateUser -> {}
        }
    }
}