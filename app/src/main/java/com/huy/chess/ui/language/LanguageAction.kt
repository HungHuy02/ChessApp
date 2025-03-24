package com.huy.chess.ui.language

sealed class LanguageAction {
    data object ClickedBack : LanguageAction()
    data object ClickedSelectLanguage : LanguageAction()
    data object ToggleForceEnglish : LanguageAction()
    data object ToggleDisplayInEnglish : LanguageAction()
}