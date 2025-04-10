package com.huy.chess.ui.selectdate

sealed class SelectDateEffect {
    data object PopBackStack: SelectDateEffect()
}