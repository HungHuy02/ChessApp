package com.huy.chess.ui.setuptwopeople

import com.huy.chess.utils.enums.TimeType

sealed class SetupTwoPeopleAction {
    data object ClickShowMore: SetupTwoPeopleAction()
    data class ClickedButton(val timeType: TimeType): SetupTwoPeopleAction()
    data object ClickedBack: SetupTwoPeopleAction()
    data class ChangeWhiteName(val value: String): SetupTwoPeopleAction()
    data class ChangeBlackName(val value: String): SetupTwoPeopleAction()
    data object ClickedChange: SetupTwoPeopleAction()
    data object ChangeRotateBoard: SetupTwoPeopleAction()
    data object ClickedPlay: SetupTwoPeopleAction()
}