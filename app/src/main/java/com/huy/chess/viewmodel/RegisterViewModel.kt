package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.register.RegisterAction
import com.huy.chess.ui.register.RegisterEffect
import com.huy.chess.ui.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel<RegisterState, RegisterAction, RegisterEffect>(RegisterState.Default) {

    override fun processAction(action: RegisterAction) {
        when(action) {
            RegisterAction.RegisterWithEmail -> sendEffect(RegisterEffect.NavigateToEmailInput)
            RegisterAction.RegisterWithFacebook -> sendEffect(RegisterEffect.SignInFacebook)
            RegisterAction.RegisterWithGoogle -> sendEffect(RegisterEffect.SignInGoogle)
        }

    }

}