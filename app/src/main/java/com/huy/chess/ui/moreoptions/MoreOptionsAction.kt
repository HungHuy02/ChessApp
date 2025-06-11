package com.huy.chess.ui.moreoptions

sealed class MoreOptionsAction {
    data object ClickedProfile: MoreOptionsAction()
    data object ClickedSettings: MoreOptionsAction()
    data object ClickedLogout: MoreOptionsAction()
}