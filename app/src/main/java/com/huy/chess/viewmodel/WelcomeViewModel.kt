package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.base.NoAction
import com.huy.chess.data.database.repositories.LocalHistoryRepository
import com.huy.chess.data.model.History
import com.huy.chess.data.network.repository.HistoryRepository
import com.huy.chess.data.network.repository.PuzzleRepository
import com.huy.chess.data.network.repository.UserRepository
import com.huy.chess.data.network.socket.GameSocket
import com.huy.chess.data.preferences.dailyPuzzleDataStore
import com.huy.chess.data.preferences.puzzleDataStore
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.proto.Puzzle
import com.huy.chess.ui.welcome.WelcomeEffect
import com.huy.chess.ui.welcome.WelcomeState
import com.huy.chess.utils.Utils
import com.huy.chess.utils.toHistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val puzzleRepository: PuzzleRepository,
    private val historyRepository: HistoryRepository,
    @ApplicationContext private val context: Context,
    private val dataStoreService: DataStoreService,
    private val socket: GameSocket,
    private val localHistoryRepository: LocalHistoryRepository
) : BaseViewModel<WelcomeState, NoAction, WelcomeEffect>(WelcomeState.Default){

    init {
        viewModelScope.launch {
            val uuid = dataStoreService.getUUID()
            if(uuid.isEmpty()) dataStoreService.setUUID(UUID.randomUUID().toString())
            val puzzle = context.puzzleDataStore.data.first()
            userRepository.getDetails()
                .onSuccess {response ->
                    context.userDataStore.updateData {
                        it.toBuilder()
                            .setIsLogin(true)
                            .setId(response.id)
                            .setName(response.name)
                            .setEmail(response.email)
                            .setAvatar(response.avatar ?: "")
                            .setElo(response.elo ?: 800)
                            .setPuzzleElo(response.puzzleElo ?: 800)
                            .build()
                    }
                    if (puzzle.fen.isNullOrEmpty()) {
                        getPuzzle(response.puzzleElo ?: 800)
                    }
                }
                .onFailure {
                    if (puzzle.fen.isNullOrEmpty()) {
                        getPuzzleGuest(800)
                    }
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
            historyRepository.getHistories(1)
                .onSuccess { list ->
                    val histories: List<History> = list
                    localHistoryRepository.insertAll(
                        histories.map { item ->
                            item.toHistoryEntity()
                        }
                    )
                }
            socket.connect(uuid)
            delay(1)
            sendEffect(WelcomeEffect.NavigateHome)
        }
    }

    private suspend fun getPuzzle(puzzleElo: Int) {
        puzzleRepository.getPuzzle(puzzleElo)
            .onSuccess { puzzle ->
                context.puzzleDataStore.updateData {
                    Puzzle.newBuilder()
                        .setId(puzzle.id)
                        .setFen(puzzle.fen)
                        .setMoves(puzzle.moves)
                        .setRating(puzzle.rating)
                        .setThemes(puzzle.themes)
                        .build()
                }
            }
    }

    private suspend fun getPuzzleGuest(puzzleElo: Int) {
        puzzleRepository.getPuzzleGuest(puzzleElo)
            .onSuccess { puzzle ->
                context.puzzleDataStore.updateData {
                    Puzzle.newBuilder()
                        .setId(puzzle.id)
                        .setFen(puzzle.fen)
                        .setMoves(puzzle.moves)
                        .setRating(puzzle.rating)
                        .setThemes(puzzle.themes)
                        .build()
                }
            }
    }

    override fun processAction(action: NoAction) { }
}