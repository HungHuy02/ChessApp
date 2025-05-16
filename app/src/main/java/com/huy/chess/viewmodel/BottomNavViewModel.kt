package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.model.User
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.BottomNavAction
import com.huy.chess.ui.BottomNavEffect
import com.huy.chess.ui.BottomNavState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<BottomNavState, BottomNavAction, BottomNavEffect>(BottomNavState()) {

    init {
        viewModelScope.launch {
            context.userDataStore.data.collect {user ->
                updateState { it.copy(
                    isLogin = user.isLogin,
                    user = User(
                        name = user.name,
                        email = user.email,
                        avatar = user.avatar,
                        elo = user.elo
                    )
                ) }
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