package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.base.NoAction
import com.huy.chess.data.network.repository.PuzzleRepository
import com.huy.chess.data.network.repository.UserRepository
import com.huy.chess.data.preferences.dailyPuzzleDataStore
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.welcome.WelcomeEffect
import com.huy.chess.ui.welcome.WelcomeState
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val puzzleRepository: PuzzleRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel<WelcomeState, NoAction, WelcomeEffect>(WelcomeState.Default){

    init {
        viewModelScope.launch {
            userRepository.getDetails()
                .onSuccess {response ->
                    context.userDataStore.updateData {
                        it.toBuilder()
                            .setIsLogin(true)
                            .setName(response.name)
                            .setEmail(response.email)
                            .setAvatar(response.avatar ?: "")
                            .setElo(800)
                            .build()
                    }
                }
                .onFailure {
                }
            puzzleRepository.getDailyPuzzle(Utils.getToday())
                .onSuccess { dailyPuzzle ->
                    context.dailyPuzzleDataStore.updateData {
                        it.toBuilder()
                            .setFen(dailyPuzzle.fen)
                            .setDate(dailyPuzzle.date)
                            .setMoves(dailyPuzzle.moves)
                            .setTitle(dailyPuzzle.title)
                            .setVideoUrl(dailyPuzzle.videoUrl)
                            .setCommentCount(dailyPuzzle.commentCount)
                            .setSolvedCount(dailyPuzzle.solvedCount)
                            .build()
                    }
                }
            delay(1)
            sendEffect(WelcomeEffect.NavigateHome)
        }
    }

    override fun processAction(action: NoAction) { }
}