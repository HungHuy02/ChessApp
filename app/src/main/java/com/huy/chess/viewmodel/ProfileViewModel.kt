package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.globalstate.UserState
import com.huy.chess.ui.profile.ProfileAction
import com.huy.chess.ui.profile.ProfileEffect
import com.huy.chess.ui.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userState: UserState
) : BaseViewModel<ProfileState, ProfileAction, ProfileEffect>(ProfileState()) {

    init {
        viewModelScope.launch {
            userState.state.collect { user ->
                updateState { it.copy(user = user) }
            }
        }
    }

    override fun processAction(action: ProfileAction) {
        when(action) {
            ProfileAction.ClickedBack -> sendEffect(ProfileEffect.PopBackStack)
            ProfileAction.ClickedEdit -> {
                updateState { it.copy(showMore = false) }
                sendEffect(ProfileEffect.NavigateEditProfile)
            }
            ProfileAction.ClickedFriends -> sendEffect(ProfileEffect.NavigateFriends)
            ProfileAction.ClickedMore -> updateState { it.copy(showMore = !it.showMore) }
            ProfileAction.ClickedQR -> {}
            ProfileAction.ClickedRecentGames -> sendEffect(ProfileEffect.NavigateGameArchive)
            ProfileAction.ClickedShareProfile -> {
                updateState { it.copy(showMore = false) }
            }
        }
    }
}