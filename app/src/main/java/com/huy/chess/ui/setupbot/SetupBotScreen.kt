package com.huy.chess.ui.setupbot

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.RowItem
import com.huy.chess.ui.component.RowItemWithSwitch
import com.huy.chess.ui.component.RowTimeButton
import com.huy.chess.ui.component.TimeButton
import com.huy.chess.ui.setupbot.composables.IconWithText
import com.huy.chess.ui.setupbot.composables.LevelSelect
import com.huy.chess.ui.setupbot.composables.PieceSelect
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleAction
import com.huy.chess.utils.enums.TimeType
import com.huy.chess.utils.toName
import com.huy.chess.viewmodel.SetupBotViewModel

@Composable
fun SetupBotScreen(
    viewModel: SetupBotViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SetupBotEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: SetupBotState,
    onAction: (SetupBotAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ChessTopAppBar(
            title = stringResource(R.string.play_with_bot_text),
            onClickBack = { onAction(SetupBotAction.ClickedBack) }
        )
        IconWithText()
        Text(
            text = stringResource(R.string.play_with_color_text),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        PieceSelect(
            side = state.side,
            onClick = { onAction(SetupBotAction.ClickedSide(it)) }
        )
        Text(
            text = stringResource(R.string.level_text),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        LevelSelect(
            onClick = { onAction(SetupBotAction.ClickedLevel(it)) },
            level = state.stockfishBotLevel
        )
        RowItem(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.time_control_text),
            text = stringResource(state.selectedTime.toName()),
            onClick = {
                onAction(SetupBotAction.ClickShowMore)
            }
        )
        AnimatedVisibility(state.showTimeControl) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RowTimeButton(
                    times = listOf(
                        TimeType.THIRTY_MINUTES,
                        TimeType.FIFTEEN_MINUTES_PLUS_TEN,
                        TimeType.TEN_MINUTES
                    ),
                    selectedTime = state.selectedTime,
                    onClick = { onAction(SetupBotAction.ClickedButton(it)) }

                )
                RowTimeButton(
                    times = listOf(
                        TimeType.FIVE_MINUTES_PLUS_FIVE,
                        TimeType.THREE_MINUTES_PLUS_TWO,
                        TimeType.TWO_MINUTES_PLUS_ONE
                    ),
                    selectedTime = state.selectedTime,
                    onClick = { onAction(SetupBotAction.ClickedButton(it)) }

                )
                RowTimeButton(
                    times = listOf(
                        TimeType.FIVE_MINUTES,
                        TimeType.THREE_MINUTES,
                        TimeType.ONE_MINUTE
                    ),
                    selectedTime = state.selectedTime,
                    onClick = { onAction(SetupBotAction.ClickedButton(it)) }
                )
                TimeButton(
                    modifier = Modifier.fillMaxWidth(),
                    timeType = TimeType.UNLIMITED,
                    isSelected = state.selectedTime == TimeType.UNLIMITED,
                    onClick = { onAction(SetupBotAction.ClickedButton(it)) }
                )

            }
        }
        RowItemWithSwitch(
            label = stringResource(R.string.suggest_text),
            modifier = Modifier.fillMaxWidth()
        )
        RowItemWithSwitch(
            label = stringResource(R.string.takeback_text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            onClick = {},
            text = stringResource(R.string.play_text),
            iconPosition = IconPosition.NONE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}