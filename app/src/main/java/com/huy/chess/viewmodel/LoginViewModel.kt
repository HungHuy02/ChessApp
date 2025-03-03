package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.contract.LoginEvent
import com.huy.chess.contract.LoginIntent
import com.huy.chess.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginState, LoginIntent, LoginEvent>(
        LoginState()
    ) {

    override fun processIntent(intent: LoginIntent) {
        when (intent) {
            LoginIntent.ClickedLoginButton -> sendEvent(LoginEvent.NavigateToHome)
            LoginIntent.ClickedLoginFacebookButton -> TODO()
            LoginIntent.ClickedLoginGoogleButton -> TODO()
            is LoginIntent.AccountChange -> updateState { updateAccount(it, intent.text) }
            is LoginIntent.PasswordChange -> updateState { updatePassword(it, intent.text) }
        }
    }

    private fun updateAccount(state: LoginState, text: String): LoginState {
        return state.copy(
            account = text
        )
    }

    private fun updatePassword(state: LoginState, text: String): LoginState {
        return state.copy(
            password = text
        )
    }
}