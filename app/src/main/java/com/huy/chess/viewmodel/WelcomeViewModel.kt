package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.base.NoAction
import com.huy.chess.data.globalstate.UserState
import com.huy.chess.data.network.repository.UserRepository
import com.huy.chess.ui.welcome.WelcomeEffect
import com.huy.chess.ui.welcome.WelcomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userState: UserState
) : BaseViewModel<WelcomeState, NoAction, WelcomeEffect>(WelcomeState.Default){

    init {
        viewModelScope.launch {
            userRepository.getDetails()
                .onSuccess {
                    userState.updateUser(it.name, it.email, it.avatar)
                }
                .onFailure {  }
            delay(400)
            sendEffect(WelcomeEffect.NavigateHome)
        }
    }

    override fun processAction(action: NoAction) { }
}