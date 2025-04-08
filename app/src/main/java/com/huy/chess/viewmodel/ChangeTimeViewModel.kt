package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.settingsDataStore
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
            context.settingsDataStore.data.collect {settings ->
                updateState { it.copy(selectedTime = enumValues<TimeType>()[settings.selectedTime]) }
            }
        }
    }

    override fun processAction(action: ChangeTimeAction) {
        when (action) {
            ChangeTimeAction.ToggleShowMore -> updateState { it.copy(isMoreSetting = !it.isMoreSetting) }
            is ChangeTimeAction.ClickedButton -> {
                updateState { it.copy(selectedTime = action.selectedTime) }
                viewModelScope.launch {
                    context.settingsDataStore.updateData {
                        it.toBuilder().setSelectedTime(action.selectedTime.ordinal).build()
                    }
                }
            }
            ChangeTimeAction.ClickedBack -> sendEffect(ChangeTimeEffect.PopBackStack)
        }
    }
}