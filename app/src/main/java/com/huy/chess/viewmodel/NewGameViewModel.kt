package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.changeTimeDataStore
import com.huy.chess.ui.newgame.NewGameAction
import com.huy.chess.ui.newgame.NewGameEffect
import com.huy.chess.ui.newgame.NewGameState
import com.huy.chess.utils.enums.TimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    BaseViewModel<NewGameState, NewGameAction, NewGameEffect>(NewGameState()) {
    init {
        viewModelScope.launch {
            context.changeTimeDataStore.data.collect { settings ->
                updateState { it.copy(selectedTime = enumValues<TimeType>()[settings.selectedTime]) }
            }
        }
    }

    override fun processAction(action: NewGameAction) {
        when (action) {
            NewGameAction.ClickedBot -> sendEffect(NewGameEffect.NavigateSetupBot)
            NewGameAction.ClickedChangeTime -> sendEffect(NewGameEffect.NavigateChangeTime)
            NewGameAction.ClickedCustom -> sendEffect(NewGameEffect.NavigateCustom)
            NewGameAction.ClickedMore -> updateState { it.copy(isShowMore = !it.isShowMore) }
            NewGameAction.ClickedPlay -> sendEffect(NewGameEffect.NavigatePlay)
            NewGameAction.ClickedTwo -> sendEffect(NewGameEffect.NavigateSetupTwo)
            NewGameAction.ClickedBack -> sendEffect(NewGameEffect.PopBackStack)
        }
    }
}