package com.huy.chess.ui.setuptwopeople

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.huy.chess.ui.setuptwopeople.composables.NameArea
import com.huy.chess.ui.setuptwopeople.composables.SetupTwoTimeControl
import com.huy.chess.utils.toName
import com.huy.chess.viewmodel.SetupTwoPeopleViewModel

@Composable
fun SetupTwoPeopleScreen(
    viewModel: SetupTwoPeopleViewModel = hiltViewModel(),
    navigatePlay: () -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SetupTwoPeopleEffect.PopBackStack -> popBackStack()
                is SetupTwoPeopleEffect.NavigatePlay -> navigatePlay()
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        ChessTopAppBar(
            title = stringResource(R.string.pass_and_play_text),
            onClickBack = { onAction(SetupTwoPeopleAction.ClickedBack) }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.play_with_friend_offline),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(16.dp))
        NameArea(
            whiteName = state.whiteName,
            onWhiteNameChange = { onAction(SetupTwoPeopleAction.ChangeWhiteName(it)) },
            blackName = state.blackName,
            onBlackNameChange = { onAction(SetupTwoPeopleAction.ChangeBlackName(it)) },
            onClickChange = { onAction(SetupTwoPeopleAction.ClickedChange) }
        )
        RowItem(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.time_control_text),
            text = stringResource(state.selectedTime.toName()),
            onClick = {
                onAction(SetupTwoPeopleAction.ClickShowMore)
            }
        )
        AnimatedVisibility(state.showTimeControl) {
            SetupTwoTimeControl(
                selectedTime = state.selectedTime
            ) { onAction(SetupTwoPeopleAction.ClickedButton(it)) }
        }
        RowItemWithSwitch(
            label = stringResource(R.string.rotate_board_text),
            checked = state.enableRotateBoard,
            onCheckedChange = { onAction(SetupTwoPeopleAction.ChangeRotateBoard) }
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            onClick = { onAction(SetupTwoPeopleAction.ClickedPlay) },
            text = stringResource(R.string.play_text),
            iconPosition = IconPosition.NONE
        )
    }
}
