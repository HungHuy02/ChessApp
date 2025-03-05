package com.huy.chess.ui.register

sealed class RegisterAction {
    data object RegisterWithEmail : RegisterAction()
    data object RegisterWithGoogle : RegisterAction()
    data object RegisterWithFacebook : RegisterAction()
}