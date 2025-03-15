package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.emailinput.EmailInputAction
import com.huy.chess.ui.emailinput.EmailInputEffect
import com.huy.chess.ui.emailinput.EmailInputState
import com.huy.chess.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailInputViewModel @Inject constructor() :
    BaseViewModel<EmailInputState, EmailInputAction, EmailInputEffect>(EmailInputState()) {

    override fun processAction(action: EmailInputAction) {
        when (action) {
            is EmailInputAction.ClickedButton -> sendEffect(EmailInputEffect.NavigateToPasswordInput)
            is EmailInputAction.InputChanged -> updateState {
                handleInputTextChange(it, action.text)
            }
        }
    }

    private fun handleInputTextChange(state: EmailInputState, text: String): EmailInputState {
        return state.copy(
            isButtonEnable = text.isValidEmail()
        )
    }
}