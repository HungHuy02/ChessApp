package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.database.entities.HistoryEntity
import com.huy.chess.data.database.repositories.LocalHistoryRepository
import com.huy.chess.data.preferences.dailyPuzzleDataStore
import com.huy.chess.data.preferences.puzzleDataStore
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.proto.DailyPuzzle
import com.huy.chess.proto.Puzzle
import com.huy.chess.proto.User
import com.huy.chess.ui.home.HomeAction
import com.huy.chess.ui.home.HomeEffect
import com.huy.chess.ui.home.HomeState
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localHistoryRepository: LocalHistoryRepository
) : BaseViewModel<HomeState, HomeAction, HomeEffect>(HomeState()){

    init {
        viewModelScope.launch {
            combine(
                context.dailyPuzzleDataStore.data,
                context.userDataStore.data,
                localHistoryRepository.getHistoriesForPage(1),
                context.puzzleDataStore.data
            ) { dailyPuzzle, user, histories, puzzle ->
                CombinedData(dailyPuzzle, user, histories, puzzle)
            }.collectLatest { (dailyPuzzle, user, histories, puzzle) ->
                val today = Utils.getToday()
                updateState {
                    it.copy(
                        dailyPuzzleFen = if (dailyPuzzle.date == today) dailyPuzzle.fen ?: "" else "",
                        totalSolved = if (dailyPuzzle.date == today) dailyPuzzle.solvedCount else 0,
                        isLogin = user.isLogin,
                        listHistory = histories,
                        puzzleFen = puzzle.fen ?: ""
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

data class CombinedData(
    val dailyPuzzle: DailyPuzzle,
    val user: User,
    val histories: List<HistoryEntity>,
    val puzzle: Puzzle
)