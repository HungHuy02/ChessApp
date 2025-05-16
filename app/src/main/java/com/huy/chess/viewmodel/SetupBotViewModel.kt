package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.setupBotDataStore
import com.huy.chess.ui.setupbot.SetupBotAction
import com.huy.chess.ui.setupbot.SetupBotEffect
import com.huy.chess.ui.setupbot.SetupBotState
import com.huy.chess.utils.enums.TimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupBotViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<SetupBotState, SetupBotAction, SetupBotEffect>( SetupBotState() ){

    init {
        viewModelScope.launch {
            context.setupBotDataStore.data.collect {settings ->
                updateState { it.copy(selectedTime = enumValues<TimeType>()[settings.selectedTime]) }
            }
        }
    }

    override fun processAction(action: SetupBotAction) {
        when(action) {
            SetupBotAction.ClickedBack -> sendEffect(SetupBotEffect.PopBackStack)
            is SetupBotAction.ClickedButton -> {
                viewModelScope.launch {
                    context.setupBotDataStore.updateData {
                        it.toBuilder().setSelectedTime(action.timeType.ordinal).build()
                    }
                }
            }
            SetupBotAction.ClickShowMore -> updateState { it.copy(showTimeControl = !it.showTimeControl) }
            is SetupBotAction.ClickedSide -> {
                if(state.value.side != action.side)
                    updateState { it.copy(side = action.side) }
            }
            is SetupBotAction.ClickedLevel -> {
                if(state.value.stockfishBotLevel != action.level)
                    updateState { it.copy(stockfishBotLevel = action.level) }
            }
            SetupBotAction.ClickedPlay -> sendEffect(SetupBotEffect.NavigatePlayBot(state.value.stockfishBotLevel))
        }
    }
}