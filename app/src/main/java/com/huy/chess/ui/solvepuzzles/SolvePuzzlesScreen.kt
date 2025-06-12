package com.huy.chess.ui.solvepuzzles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.solvepuzzles.composable.PuzzleBottomBar
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescription
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.viewmodel.SolvePuzzlesViewModel

@Composable
fun SolvePuzzlesScreen(
    viewModel: SolvePuzzlesViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SolvePuzzlesEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: SolvePuzzlesState,
    onAction: (SolvePuzzlesAction) -> Unit
) {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, des, board, bottomBar) = createRefs()
        ChessTopAppBar(
            onClickBack = { onAction(SolvePuzzlesAction.ClickedBackArrow) },
            title = stringResource(R.string.puzzle_text),
            icon = painterResource(R.drawable.puzzles),
            onAction = {
                Text(
                    text = "\u00B7",
                    fontFamily = ChessGlyphFontFamily,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {

                        }
                )
            },
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        PuzzleDescription(
            type = state.type,
            modifier = Modifier.constrainAs(des) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(board.top, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        state.fen?.let {
            ChessBoard(
                fen = it,
                onMove = { onAction(SolvePuzzlesAction.Move(it)) },
                isPuzzleEnd = false,
                correctMove = if(state.showSuggest) state.correctMove else null,
                nextMove = state.nextMove,
                isReplay = state.isRePlay,
                side = state.side,
                modifier = Modifier.constrainAs(board) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        PuzzleBottomBar(
            type = state.type,
            onClick = onAction,
            modifier = Modifier.constrainAs(bottomBar) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}