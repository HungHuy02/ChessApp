package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.globalstate.UserState
import com.huy.chess.ui.BottomNavAction
import com.huy.chess.ui.BottomNavEffect
import com.huy.chess.ui.BottomNavState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavViewModel @Inject constructor(
    private val userState: UserState
) : BaseViewModel<BottomNavState, BottomNavAction, BottomNavEffect>(BottomNavState()) {

    init {
        viewModelScope.launch {
            userState.state.collect {user ->
                updateState { it.copy(user = user) }
            }
        }
    }

    override fun processAction(action: BottomNavAction) {
        when(action) {
            BottomNavAction.ClickedFriends -> sendEffect(BottomNavEffect.NavigateFriends)
            BottomNavAction.ClickedLogin -> sendEffect(BottomNavEffect.NavigateLogin)
            BottomNavAction.ClickedNotification -> sendEffect(BottomNavEffect.NavigateNotification)
            BottomNavAction.ClickedProfile -> sendEffect(BottomNavEffect.NavigateProfile)
            BottomNavAction.ClickedRegister -> sendEffect(BottomNavEffect.NavigateRegister)
        }
    }

}