package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.contract.EmailInputEvent
import com.huy.chess.contract.EmailInputIntent
import com.huy.chess.contract.EmailInputState
import com.huy.chess.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailInputViewModel @Inject constructor() :
    BaseViewModel<EmailInputState, EmailInputIntent, EmailInputEvent>(EmailInputState()) {

    override fun processIntent(intent: EmailInputIntent) {
        when (intent) {
            is EmailInputIntent.ClickedButton -> sendEvent(EmailInputEvent.NavigateToPasswordInput)
            is EmailInputIntent.InputChanged -> updateState {
                handleInputTextChange(it, intent.text)
            }
        }
    }

    private fun handleInputTextChange(state: EmailInputState, text: String): EmailInputState {
        return state.copy(
            inputText = text,
            isButtonEnable = text.isValidEmail()
        )
    }
}