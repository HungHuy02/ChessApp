package com.huy.chess.ui.profile

sealed class ProfileAction {
    data object ClickedBack: ProfileAction()
    data object ClickedMore: ProfileAction()
    data object ClickedQR: ProfileAction()
    data object ClickedRecentGames: ProfileAction()
    data object ClickedFriends: ProfileAction()
    data object ClickedEdit: ProfileAction()
    data object ClickedShareProfile: ProfileAction()
}