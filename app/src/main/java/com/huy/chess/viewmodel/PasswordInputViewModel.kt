package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.passwordinput.PasswordInputEffect
import com.huy.chess.ui.passwordinput.PasswordInputAction
import com.huy.chess.ui.passwordinput.PasswordInputState
import com.huy.chess.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordInputViewModel @Inject constructor() :
    BaseViewModel<PasswordInputState, PasswordInputAction, PasswordInputEffect>(PasswordInputState()) {
    override fun processAction(action: PasswordInputAction) {
        when(action) {
            PasswordInputAction.ClickedButton -> sendEffect(PasswordInputEffect.NavigateToProfileSetup)
            is PasswordInputAction.InputChanged -> updateState {
                handleInputTextChange(it, action.text)
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