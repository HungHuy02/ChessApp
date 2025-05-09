package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.network.repository.AuthRepository
import com.huy.chess.ui.emailinput.EmailInputAction
import com.huy.chess.ui.emailinput.EmailInputEffect
import com.huy.chess.ui.emailinput.EmailInputState
import com.huy.chess.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailInputViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :
    BaseViewModel<EmailInputState, EmailInputAction, EmailInputEffect>(EmailInputState()) {

    override fun processAction(action: EmailInputAction) {
        when (action) {
            is EmailInputAction.ClickedButton -> {
                if(action.email.isValidEmail())
                    viewModelScope.launch {
                        authRepository.verifyEmail(action.email)
                            .onSuccess {
                                sendEffect(EmailInputEffect.NavigateToPasswordInput)
                            }
                            .onFailure {
                                updateState { it.copy(showAccountExists = true, showNotValid = false) }
                            }
                    }
                else
                    updateState { it.copy(showNotValid = true, showAccountExists = false) }
            }
            EmailInputAction.InputChange -> updateState { it.copy(showNotValid = false, showAccountExists = false) }
            EmailInputAction.ClickedLogin -> sendEffect(EmailInputEffect.NavigateLogin)
        }
    }
}