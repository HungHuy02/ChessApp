package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.changeTimeDataStore
import com.huy.chess.ui.changetime.ChangeTimeAction
import com.huy.chess.ui.changetime.ChangeTimeEffect
import com.huy.chess.ui.changetime.ChangeTimeState
import com.huy.chess.utils.enums.TimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeTimeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<ChangeTimeState, ChangeTimeAction, ChangeTimeEffect>(ChangeTimeState()) {

    init {
        viewModelScope.launch {
            context.changeTimeDataStore.data.collect {settings ->
                updateState {
                    it.copy(
                        selectedTime = enumValues<TimeType>()[settings.selectedTime],
                        initTime = settings.initTime,
                        plusTime = settings.plusTime
                    )
                }
            }
        }
    }

    override fun processAction(action: ChangeTimeAction) {
        when (action) {
            ChangeTimeAction.ToggleShowMore -> updateState { it.copy(isMoreSetting = !it.isMoreSetting) }
            is ChangeTimeAction.ClickedButton -> {
                updateState { it.copy(selectedTime = action.selectedTime) }
                viewModelScope.launch {
                    context.changeTimeDataStore.updateData {
                        it.toBuilder().setSelectedTime(action.selectedTime.ordinal).build()
                    }
                }
            }
            ChangeTimeAction.ClickedBack -> sendEffect(ChangeTimeEffect.PopBackStack)
        }
    }
}