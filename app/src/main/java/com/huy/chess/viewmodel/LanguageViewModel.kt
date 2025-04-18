package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.languageDataStore
import com.huy.chess.ui.language.LanguageAction
import com.huy.chess.ui.language.LanguageEffect
import com.huy.chess.ui.language.LanguageState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    BaseViewModel<LanguageState, LanguageAction, LanguageEffect>( LanguageState() )
{
    init {
        viewModelScope.launch {
            context.languageDataStore.data.collect {language ->
                updateState { it.copy(
                    isDisplayInEnglishWhenNotAvailable = language.displayEnglishWhenNotAvailable,
                    isForceLanguage = language.forceLanguage
                ) }
            }
        }
    }

    override fun processAction(action: LanguageAction) {
        when(action) {
            LanguageAction.ClickedBack -> sendEffect(LanguageEffect.PopBackStack)
            LanguageAction.ClickedSelectLanguage -> {}
            LanguageAction.ToggleDisplayInEnglish -> {
                viewModelScope.launch {
                    context.languageDataStore.updateData {
                        it.toBuilder()
                            .setDisplayEnglishWhenNotAvailable(!state.value.isDisplayInEnglishWhenNotAvailable)
                            .build()
                    }
                }
            }
            LanguageAction.ToggleForceEnglish -> {
                viewModelScope.launch {
                    context.languageDataStore.updateData {
                        it.toBuilder().setForceLanguage(!state.value.isForceLanguage).build()
                    }
                }
            }
        }
    }

}