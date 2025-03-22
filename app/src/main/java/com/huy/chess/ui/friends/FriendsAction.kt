package com.huy.chess.ui.friends

sealed class FriendsAction {
    data object ClickedBack: FriendsAction()
    data object ClickedQR: FriendsAction()
    data object ClickedSearchContact: FriendsAction()
    data object ClickedFacebookFriends: FriendsAction()
    data object ClickedInviteFriends: FriendsAction()
    data object ClickedSendChallenge: FriendsAction()
}