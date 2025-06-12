package com.huy.chess.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.model.request.SolvedRequest
import com.huy.chess.data.network.repository.PuzzleRepository
import com.huy.chess.data.preferences.puzzleDataStore
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.proto.Puzzle
import com.huy.chess.proto.User
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesAction
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesEffect
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesState
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescriptionType
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolvePuzzlesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val puzzleRepository: PuzzleRepository
) : BaseViewModel<SolvePuzzlesState, SolvePuzzlesAction, SolvePuzzlesEffect>(
    SolvePuzzlesState()
){
    private var getNewPuzzle = false
    init {
        viewModelScope.launch {
            combine(
                context.userDataStore.data,
                context.puzzleDataStore.data
            ) { user, puzzle ->
                Pair(user, puzzle)
            }.collect {(user, puzzle) ->
                if(state.value.type == PuzzleDescriptionType.NONE) {
                    val side = parseFen(puzzle.fen)
                    val moves = puzzle.moves.split(" ")
                    val isOpponentMoveFirst = moves.size % 2 == 0
                    updateState { it.copy(
                        puzzleMove = moves,
                        type = if(isOpponentMoveFirst && !side) PuzzleDescriptionType.WhiteMove else PuzzleDescriptionType.BlackMove,
                        isLogin = user.isLogin,
                        puzzleId = puzzle.id,
                        rating = puzzle.rating,
                        fen = puzzle.fen,
                        nextMove = if(isOpponentMoveFirst) moves[0] else null,
                        side = isOpponentMoveFirst && !side,
                        puzzleStep = if(isOpponentMoveFirst) 1 else 0,
                    ) }
                } else {
                    updateState { it.copy(
                        puzzle = puzzle,
                        userElo = user.elo
                    ) }
                }
            }
        }
    }

    override fun processAction(action: SolvePuzzlesAction) {
        when(action) {
            SolvePuzzlesAction.ClickedAnswer -> {}
            SolvePuzzlesAction.ClickedBack -> {}
            SolvePuzzlesAction.ClickedContinue -> {
                getNewPuzzle = false
                val puzzle = state.value.puzzle
                val side = parseFen(puzzle!!.fen)
                val moves = puzzle.moves.split(" ")
                val isOpponentMoveFirst = moves.size % 2 == 0
                updateState { it.copy(
                    puzzleMove = moves,
                    type = if(!side && isOpponentMoveFirst) PuzzleDescriptionType.WhiteMove else PuzzleDescriptionType.BlackMove,
                    puzzleId = puzzle.id,
                    rating = puzzle.rating,
                    puzzleStep = if(isOpponentMoveFirst) 1 else 0,
                    fen = puzzle.fen,
                    nextMove = if(isOpponentMoveFirst) moves[0] else null,
                    side = !side && isOpponentMoveFirst,
                    showSuggest = false,
                    correctMove = if(isOpponentMoveFirst) moves.getOrNull(1) else moves[0]
                ) }
            }
            SolvePuzzlesAction.ClickedRetry -> {
                val value = state.value
                val side = parseFen(value.fen!!)
                val isOpponentMoveFirst = value.puzzleMove.size % 2 == 0
                updateState { it.copy(
                    type = if(!side && isOpponentMoveFirst) PuzzleDescriptionType.WhiteMove else PuzzleDescriptionType.BlackMove,
                    puzzleStep = if(isOpponentMoveFirst) 1 else 0,
                    nextMove = if(isOpponentMoveFirst) value.puzzleMove[0] else null,
                    side = !side && isOpponentMoveFirst,
                    isRePlay = true,
                    showSuggest = false,
                    correctMove = if(isOpponentMoveFirst) value.puzzleMove.getOrNull(1) else value.puzzleMove[0]
                ) }
            }
            SolvePuzzlesAction.ClickedSuggestion -> {
                updateState { it.copy(showSuggest = true) }
            }
            is SolvePuzzlesAction.Move -> {
                val value = state.value
                if(action.move == value.puzzleMove[value.puzzleStep]) {
                    if(value.puzzleStep == value.puzzleMove.size - 1) {
                        getNewPuzzle(value, true)
                        updateState { it.copy(type = PuzzleDescriptionType.Correct, showSuggest = false) }
                    } else {
                        val nextStep = value.puzzleStep + 1
                        updateState { it.copy(
                            puzzleStep = nextStep + 1,
                            nextMove = it.puzzleMove[nextStep],
                            correctMove = it.puzzleMove[nextStep + 1],
                            showSuggest = false
                        ) }
                    }
                } else {
                    getNewPuzzle(value, true)
                    updateState { it.copy(type = PuzzleDescriptionType.Fail, showSuggest = false) }
                }
            }
            SolvePuzzlesAction.ClickedAnalysis -> {}
            SolvePuzzlesAction.ClickedBackArrow -> sendEffect(SolvePuzzlesEffect.PopBackStack)
            SolvePuzzlesAction.ClickedForward -> {}
        }
    }

    private fun getNewPuzzle(value: SolvePuzzlesState, success: Boolean) {
        viewModelScope.launch {
            if(!getNewPuzzle) {
                if(value.isLogin) {
                    puzzleRepository.solved(SolvedRequest(
                            puzzleId = value.puzzleId,
                            puzzleElo = value.rating,
                            elo = value.userElo,
                            successMoves = Utils.getMoveCount(if(success) value.puzzleStep else value.puzzleStep - 1),
                            totalMoves = Utils.getMoveCount(value.puzzleMove.size - 1)
                            ))
                        .onSuccess {solved ->
                            context.userDataStore.updateData { User.newBuilder().setPuzzleElo(solved.newElo).build() }
                            savePuzzle(solved.newPuzzle)
                        }
                } else {
                    puzzleRepository.getPuzzleGuest().onSuccess { puzzle ->
                        savePuzzle(puzzle)
                    }
                }
            }
            getNewPuzzle = true
        }
    }

    suspend fun savePuzzle(puzzle: com.huy.chess.data.model.Puzzle) {
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