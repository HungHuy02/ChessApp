package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.login.LoginEffect
import com.huy.chess.ui.login.LoginAction
import com.huy.chess.ui.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginState, LoginAction, LoginEffect>(
        LoginState()
    ) {

    override fun processAction(action: LoginAction) {
        when (action) {
            LoginAction.ClickedLoginButton -> sendEffect(LoginEffect.NavigateToHome)
            LoginAction.ClickedLoginFacebookButton -> sendEffect(LoginEffect.SignInFacebook)
            LoginAction.ClickedLoginGoogleButton -> sendEffect(LoginEffect.SignInGoogle)
            is LoginAction.AccountChange -> updateState { updateAccount(it, action.text) }
            is LoginAction.PasswordChange -> updateState { updatePassword(it, action.text) }
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