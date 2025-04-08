package com.huy.chess.ui.setuptwopeople

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
import com.huy.chess.ui.setuptwopeople.composables.NameArea
import com.huy.chess.utils.enums.TimeType
import com.huy.chess.utils.toName
import com.huy.chess.viewmodel.SetupTwoPeopleViewModel

@Composable
fun SetupTwoPeopleScreen(
    viewModel: SetupTwoPeopleViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SetupTwoPeopleEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: SetupTwoPeopleState,
    onAction: (SetupTwoPeopleAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ChessTopAppBar(
            title = stringResource(R.string.pass_and_play_text),
            onClickBack = { onAction(SetupTwoPeopleAction.ClickedBack) }
        )
        Text(
            text = stringResource(R.string.play_with_friend_offline),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        NameArea()
        RowItem(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.time_control_text),
            text = stringResource(state.selectedTime.toName()),
            onClick = {
                onAction(SetupTwoPeopleAction.ClickShowMore)
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
                    onClick = { onAction(SetupTwoPeopleAction.ClickedButton(it)) }
                )
                RowTimeButton(
                    times = listOf(
                        TimeType.FIVE_MINUTES_PLUS_FIVE,
                        TimeType.THREE_MINUTES_PLUS_TWO,
                        TimeType.TWO_MINUTES_PLUS_ONE
                    ),
                    selectedTime = state.selectedTime,
                    onClick = { onAction(SetupTwoPeopleAction.ClickedButton(it)) }
                )
                RowTimeButton(
                    times = listOf(
                        TimeType.FIVE_MINUTES,
                        TimeType.THREE_MINUTES,
                        TimeType.ONE_MINUTE
                    ),
                    selectedTime = state.selectedTime,
                    onClick = { onAction(SetupTwoPeopleAction.ClickedButton(it)) }
                )
                TimeButton(
                    modifier = Modifier.fillMaxWidth(),
                    timeType = TimeType.UNLIMITED,
                    isSelected = state.selectedTime == TimeType.UNLIMITED,
                    onClick = { onAction(SetupTwoPeopleAction.ClickedButton(it)) }
                )

            }
        }
        RowItemWithSwitch(
            label = stringResource(R.string.rotate_board_text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            text = stringResource(R.string.play_text),
            iconPosition = IconPosition.NONE
        )
    }
}
