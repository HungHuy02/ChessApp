package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.dailyPuzzleDataStore
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.home.HomeAction
import com.huy.chess.ui.home.HomeEffect
import com.huy.chess.ui.home.HomeState
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<HomeState, HomeAction, HomeEffect>(HomeState()){

    init {
        viewModelScope.launch {
            context.dailyPuzzleDataStore.data.collect {puzzle ->
                if(puzzle.date == Utils.getToday())
                    updateState {
                        it.copy(
                            dailyPuzzleFen = puzzle.fen ?: "",
                            totalSolved = puzzle.solvedCount
                        )
                    }
            }
        }
        viewModelScope.launch {
            context.userDataStore.data.collect {user ->
                updateState {
                    it.copy(
                        isLogin = user.isLogin
                    )
                }
            }
        }
    }

    override fun processAction(action: HomeAction) {
        when(action) {
            HomeAction.ClickedBot -> sendEffect(HomeEffect.NavigatePlayBot)
            HomeAction.ClickedDailyPuzzle -> sendEffect(HomeEffect.NavigateDailyPuzzle)
            HomeAction.ClickedPlayOnline -> sendEffect(HomeEffect.ShowPlayOnlineDialog)
            HomeAction.ClickedPuzzle -> sendEffect(HomeEffect.NavigatePuzzle)
            HomeAction.ClickedStudy -> sendEffect(HomeEffect.NavigateStudy)
            HomeAction.ClickedPlay -> sendEffect(HomeEffect.NavigatePlay)
        }
    }
}