package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.registerway.RegisterWayAction
import com.huy.chess.ui.registerway.RegisterWayEffect
import com.huy.chess.ui.registerway.RegisterWayState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterWayViewModel @Inject constructor() : BaseViewModel<RegisterWayState, RegisterWayAction, RegisterWayEffect>(RegisterWayState.Default) {

    override fun processAction(action: RegisterWayAction) {
        when(action) {
            RegisterWayAction.RegisterWayWithEmail -> sendEffect(RegisterWayEffect.NavigateToEmailInput)
            RegisterWayAction.RegisterWayWithFacebook -> sendEffect(RegisterWayEffect.SignInFacebook)
            RegisterWayAction.RegisterWayWithGoogle -> sendEffect(RegisterWayEffect.SignInGoogle)
        }

    }

}