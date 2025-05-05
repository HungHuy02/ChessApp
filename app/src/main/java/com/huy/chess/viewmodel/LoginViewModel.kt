package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.globalstate.UserState
import com.huy.chess.data.network.repository.AuthRepository
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.data.model.request.LoginRequest
import com.huy.chess.ui.login.LoginAction
import com.huy.chess.ui.login.LoginEffect
import com.huy.chess.ui.login.LoginState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userState: UserState,
    private val authRepository: AuthRepository,
    private val dataStoreService: DataStoreService
) :
    BaseViewModel<LoginState, LoginAction, LoginEffect>( LoginState() )
{

    override fun processAction(action: LoginAction) {
        when (action) {
            LoginAction.ClickedLoginButton -> handleLogin()
            LoginAction.ClickedLoginFacebookButton -> sendEffect(LoginEffect.SignInFacebook)
            LoginAction.ClickedLoginGoogleButton -> sendEffect(LoginEffect.SignInGoogle)
            is LoginAction.AccountChange -> updateState { updateAccount(it, action.text) }
            is LoginAction.PasswordChange -> updateState { updatePassword(it, action.text) }
            LoginAction.ClickedBackButton -> sendEffect(LoginEffect.PopBackStack)
        }
    }

    private fun handleLogin() {
        val isAccountEmpty = state.value.account.isEmpty()
        val isPasswordEmpty = state.value.password.isEmpty()
        if(!isAccountEmpty && !isPasswordEmpty)
            viewModelScope.launch {
                val loginRequest = LoginRequest(state.value.account, state.value.password)
                authRepository.login(loginRequest)
                    .onSuccess {
                        userState.updateUser(it.name, it.email, it.avatar)
                        dataStoreService.setAccessToken(Utils.encodeAESCBC(it.accessToken, Constants.ACCESS_TOKEN_ALIAS))
                        dataStoreService.setRefreshToken(Utils.encodeAESCBC(it.refreshToken, Constants.REFRESH_TOKEN_ALIAS))
                        sendEffect(LoginEffect.NavigateToHome)
                    }
                    .onFailure {
                        updateState { it.copy(showError = true) }
                    }
            }
        else {
            updateState { it.copy(enableAccountError = isAccountEmpty, enablePasswordError = isPasswordEmpty) }
        }
    }

    private fun updateAccount(state: LoginState, text: String): LoginState {
        val showSocialLogin = text.isEmpty() && state.password.isEmpty()
        return state.copy(
            account = text,
            enableAccountError = false,
            showError = false,
            showSocialLogin = showSocialLogin
        )
    }

    private fun updatePassword(state: LoginState, text: String): LoginState {
        val showSocialLogin = text.isEmpty() && state.account.isEmpty()
        return state.copy(
            password = text,
            enablePasswordError = false,
            showError = false,
            showSocialLogin = showSocialLogin
        )
    }
}