package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.contract.PasswordInputEvent
import com.huy.chess.contract.PasswordInputIntent
import com.huy.chess.contract.PasswordInputState
import com.huy.chess.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordInputViewModel @Inject constructor() :
    BaseViewModel<PasswordInputState, PasswordInputIntent, PasswordInputEvent>(PasswordInputState()) {
    override fun processIntent(intent: PasswordInputIntent) {
        when(intent) {
            PasswordInputIntent.ClickedButton -> sendEvent(PasswordInputEvent.NavigateToProfileSetup)
            is PasswordInputIntent.InputChanged -> updateState {
                handleInputTextChange(it, intent.text)
            }
        }
    }

    private fun handleInputTextChange(state: PasswordInputState, text: String): PasswordInputState {
        return state.copy(
            inputText = text,
            isButtonEnable = text.isValidPassword()
        )
    }
}