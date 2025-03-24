package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.language.LanguageAction
import com.huy.chess.ui.language.LanguageEffect
import com.huy.chess.ui.language.LanguageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor() :
    BaseViewModel<LanguageState, LanguageAction, LanguageEffect>(
        LanguageState()
    ) {
    override fun processAction(action: LanguageAction) {
        when(action) {
            LanguageAction.ClickedBack -> sendEffect(LanguageEffect.PopBackStack)
            LanguageAction.ClickedSelectLanguage -> {}
            LanguageAction.ToggleDisplayInEnglish -> updateState { it.copy(isForceLanguage = !it.isForceLanguage) }
            LanguageAction.ToggleForceEnglish -> updateState { it.copy(isDisplayInEnglishWhenNotAvailable = !it.isDisplayInEnglishWhenNotAvailable) }
        }
    }

}