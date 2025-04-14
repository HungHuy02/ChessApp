package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.base.NoAction
import com.huy.chess.ui.welcome.WelcomeEffect
import com.huy.chess.ui.welcome.WelcomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
) : BaseViewModel<WelcomeState, NoAction, WelcomeEffect>(WelcomeState.Default){

    init {
        viewModelScope.launch {
            delay(10000)
            sendEffect(WelcomeEffect.NavigateHome)
        }
    }

    override fun processAction(action: NoAction) { }
}