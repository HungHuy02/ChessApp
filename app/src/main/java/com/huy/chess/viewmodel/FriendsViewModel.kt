package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.friends.FriendsAction
import com.huy.chess.ui.friends.FriendsEffect
import com.huy.chess.ui.friends.FriendsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor() : BaseViewModel<FriendsState, FriendsAction, FriendsEffect>(FriendsState.Default) {
    override fun processAction(action: FriendsAction) {
        when(action) {
            FriendsAction.ClickedBack -> sendEffect(FriendsEffect.PopBackStack)
            FriendsAction.ClickedFacebookFriends -> {}
            FriendsAction.ClickedInviteFriends -> {}
            FriendsAction.ClickedQR -> {}
            FriendsAction.ClickedSearchContact -> {}
            FriendsAction.ClickedSendChallenge -> {}
        }
    }
}