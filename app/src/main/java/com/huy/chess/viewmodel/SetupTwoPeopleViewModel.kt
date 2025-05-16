package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.setupTwoDataStore
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
            context.setupTwoDataStore.data.collect {settings ->
                updateState {
                    it.copy(
                        selectedTime = enumValues<TimeType>()[settings.selectedTime],
                        enableRotateBoard = settings.rotateBoard
                    )
                }
            }
        }
    }

    override fun processAction(action: SetupTwoPeopleAction) {
        when(action) {
            SetupTwoPeopleAction.ClickedBack -> sendEffect(SetupTwoPeopleEffect.PopBackStack)
            is SetupTwoPeopleAction.ClickedButton -> {
                viewModelScope.launch {
                    context.setupTwoDataStore.updateData {
                        it.toBuilder().setSelectedTime(action.timeType.ordinal).build()
                    }
                }
            }
            SetupTwoPeopleAction.ClickShowMore -> updateState { it.copy(showTimeControl = !it.showTimeControl) }
            is SetupTwoPeopleAction.ChangeBlackName -> updateState { it.copy(blackName = action.value) }
            is SetupTwoPeopleAction.ChangeWhiteName -> updateState { it.copy(whiteName = action.value) }
            SetupTwoPeopleAction.ClickedChange -> updateState { it.copy(blackName = it.whiteName, whiteName = it.blackName) }
            SetupTwoPeopleAction.ChangeRotateBoard -> {
                updateState { it.copy(enableRotateBoard = !it.enableRotateBoard) }
                viewModelScope.launch {
                    context.setupTwoDataStore.updateData { it.toBuilder().setRotateBoard(state.value.enableRotateBoard).build() }
                }
            }
            SetupTwoPeopleAction.ClickedPlay -> {
                with(state.value) {
                    sendEffect(
                        SetupTwoPeopleEffect.NavigatePlay(
                            whiteName = whiteName,
                            blackName = blackName,
                            time = selectedTime,
                            enableRotate = enableRotateBoard
                        )
                    )
                }
            }
        }
    }
}