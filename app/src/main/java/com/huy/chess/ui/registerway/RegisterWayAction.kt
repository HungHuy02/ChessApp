package com.huy.chess.ui.registerway

sealed class RegisterWayAction {
    data object RegisterWayWithEmail : RegisterWayAction()
    data object RegisterWayWithGoogle : RegisterWayAction()
    data object RegisterWayWithFacebook : RegisterWayAction()
    data object ClickedBack: RegisterWayAction()
    data object ClickedLogIn: RegisterWayAction()
}