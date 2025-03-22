package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.profile.ProfileAction
import com.huy.chess.ui.profile.ProfileEffect
import com.huy.chess.ui.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileState, ProfileAction, ProfileEffect>(ProfileState.Default) {
    override fun processAction(action: ProfileAction) {
        when(action) {
            ProfileAction.ClickedBack -> sendEffect(ProfileEffect.PopBackStack)
            ProfileAction.ClickedEdit -> sendEffect(ProfileEffect.NavigateEditProfile)
            ProfileAction.ClickedFriends -> sendEffect(ProfileEffect.NavigateFriends)
            ProfileAction.ClickedMore -> {}
            ProfileAction.ClickedQR -> {}
            ProfileAction.ClickedRecentGames -> sendEffect(ProfileEffect.NavigateGameArchive)
            ProfileAction.ClickedShareProfile -> {}
        }
    }
}