package com.huy.chess.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.getChessPieceBitmap
import com.huy.chess.ui.home.composables.HomeItems
import com.huy.chess.utils.Constants
import com.huy.chess.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToPlay: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToGameArchive: () -> Unit,
    navigateToDailyPuzzle: () -> Unit,
    navigateToBot: () -> Unit,
    navigateToPuzzle: () -> Unit,
    navigateToStudy: () -> Unit,
    showPlayOnlineDialog: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                HomeEffect.NavigateDailyPuzzle -> navigateToDailyPuzzle()
                HomeEffect.NavigateGameArchive -> navigateToGameArchive()
                HomeEffect.NavigateHistory -> navigateToHistory()
                HomeEffect.NavigatePlay -> navigateToPlay()
                HomeEffect.NavigatePlayBot -> navigateToBot()
                HomeEffect.NavigatePuzzle -> navigateToPuzzle()
                HomeEffect.NavigateStudy -> navigateToStudy()
                HomeEffect.ShowPlayOnlineDialog -> showPlayOnlineDialog()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val context = LocalContext.current
    val list = remember { getChessPieceBitmap(context) }
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        if(!state.isLogin)
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    HomeItems(
                        fen = state.onlineFen,
                        list = list,
                        title = stringResource(R.string.play_online_text),
                        description = stringResource(R.string.play_online_dec_text),
                        icon = R.drawable.playwhite_cea685ba,
                        onClick = { onAction(HomeAction.ClickedPlayOnline) }
                    )
                }

                item {
                    HomeItems(
                        fen = state.puzzleFen,
                        list = list,
                        title = stringResource(R.string.solve_puzzles_text),
                        description = stringResource(R.string.solve_puzzles_dec_text),
                        icon = R.drawable.puzzles,
                        onClick = { onAction(HomeAction.ClickedPuzzle) }
                    )
                }

                item {
                    HomeItems(
                        fen = state.dailyPuzzleFen,
                        list = list,
                        title = stringResource(R.string.daily_puzzle_text),
                        description = stringResource(R.string.daily_puzzles_dec_text, state.totalSolved),
                        icon = R.drawable.dailypuzzle,
                        onClick = { onAction(HomeAction.ClickedDailyPuzzle) }
                    )
                }

                item {
                    HomeItems(
                        fen = state.botFen,
                        list = list,
                        title = stringResource(R.string.play_with_bot_text),
                        description = stringResource(R.string.play_with_bot_dec_text),
                        icon = R.drawable.stockfish,
                        onClick = { onAction(HomeAction.ClickedBot) }
                    )
                }

                item {
                    HomeItems(
                        fen = Constants.START_FEN,
                        list = list,
                        title = stringResource(R.string.study_text),
                        description = stringResource(R.string.study_dec_text),
                        icon = R.drawable.lessons,
                        onClick = { onAction(HomeAction.ClickedStudy) }
                    )
                }
            }
        else {}

        AppButton(
            onClick = { onAction(HomeAction.ClickedPlay) },
            text = stringResource(R.string.play_text),
            textColor = Color.White,
            iconPosition = IconPosition.NONE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

