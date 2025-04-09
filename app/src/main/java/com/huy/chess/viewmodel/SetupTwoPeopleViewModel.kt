package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.settingsDataStore
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleAction
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleEffect
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleState
import com.huy.chess.utils.enums.TimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupTwoPeopleViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<SetupTwoPeopleState, SetupTwoPeopleAction, SetupTwoPeopleEffect>(
    SetupTwoPeopleState()
){

    init {
        viewModelScope.launch {
            context.settingsDataStore.data.collect {settings ->
                updateState { it.copy(selectedTime = enumValues<TimeType>()[settings.selectedTimeTwo]) }
            }
        }
    }

    override fun processAction(action: SetupTwoPeopleAction) {
        when(action) {
            SetupTwoPeopleAction.ClickedBack -> sendEffect(SetupTwoPeopleEffect.PopBackStack)
            is SetupTwoPeopleAction.ClickedButton -> {
                viewModelScope.launch {
                    context.settingsDataStore.updateData {
                        it.toBuilder().setSelectedTimeTwo(action.timeType.ordinal).build()
                    }
                }
            }
            SetupTwoPeopleAction.ClickShowMore -> updateState { it.copy(showTimeControl = !it.showTimeControl) }
        }
    }
}