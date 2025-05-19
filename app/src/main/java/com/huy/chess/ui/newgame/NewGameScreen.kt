package com.huy.chess.ui.newgame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.newgame.composables.AddMoreButton
import com.huy.chess.ui.newgame.composables.NewGameButton
import com.huy.chess.ui.newgame.composables.TimeButton
import com.huy.chess.viewmodel.NewGameViewModel

@Composable
fun NewGameScreen(
    viewModel: NewGameViewModel = hiltViewModel(),
    navigateToPlayOnline: () -> Unit,
    navigateToChangeTime: () -> Unit,
    navigateToSetupBot: () -> Unit,
    navigateToSetupTwoPeople: () -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                NewGameEffect.NavigateChangeTime -> navigateToChangeTime()
                NewGameEffect.NavigateCustom -> {}
                NewGameEffect.NavigatePlay -> navigateToPlayOnline()
                NewGameEffect.NavigateSetupBot -> navigateToSetupBot()
                NewGameEffect.NavigateSetupTwo -> navigateToSetupTwoPeople()
                NewGameEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
fun Content(
    state: NewGameState,
    onAction: (NewGameAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ChessTopAppBar(
            title = stringResource(R.string.new_game_text),
            onClickBack = { onAction(NewGameAction.ClickedBack) }
        )
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TimeButton(
                timeType = state.selectedTime,
                modifier = Modifier.fillMaxWidth()
            ) {
                onAction(NewGameAction.ClickedChangeTime)
            }
            AppButton(
                onClick = { onAction(NewGameAction.ClickedPlay) },
                text = stringResource(R.string.new_game_text),
                textStyle = MaterialTheme.typography.titleLarge,
                textColor = MaterialTheme.colorScheme.onPrimary,
                iconPosition = IconPosition.NONE,
                modifier = Modifier.fillMaxWidth()
            )
            NewGameButton(
                onClick = {  },
                text = stringResource(R.string.play_with_friend_text),
                painter = painterResource(R.drawable.handshake_8c90be47)
            )
            NewGameButton(
                onClick = { onAction(NewGameAction.ClickedBot) },
                text = stringResource(R.string.play_with_bot_text),
                painter = painterResource(R.drawable.cute_bot_32735490)
            )
            AddMoreButton(
                isShow = state.isShowMore
            ) {
                onAction(NewGameAction.ClickedMore)
            }
            AnimatedVisibility(state.isShowMore) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NewGameButton(
                        onClick = {},
                        text = stringResource(R.string.custom_game_text),
                        painter = painterResource(R.drawable.tune_24px)
                    )
                    NewGameButton(
                        onClick = { onAction(NewGameAction.ClickedTwo) },
                        text = stringResource(R.string.pass_and_play_text),
                        painter = painterResource(R.drawable.friends)
                    )
                }
            }
        }
    }
}