package com.huy.chess.ui.changetime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.huy.chess.ui.changetime.composables.CustomTimeSelect
import com.huy.chess.ui.changetime.composables.IconWithText
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.RowTimeButton
import com.huy.chess.viewmodel.ChangeTimeViewModel

@Composable
fun ChangeTimeScreen(
    viewModel: ChangeTimeViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                ChangeTimeEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: ChangeTimeState,
    onAction: (ChangeTimeAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ChessTopAppBar(
            title = stringResource(R.string.time_text),
            onClickBack = { onAction(ChangeTimeAction.ClickedBack) }
        )
        IconWithText(
            painter = painterResource(R.drawable.bullet),
            text = stringResource(R.string.super_flash_text)
        )
        RowTimeButton(
            times = listOf(
                stringResource(R.string.one_minute_text),
                stringResource(R.string.one_minute_plus_one_text),
                stringResource(R.string.two_minute_plus_one_text)
            ),
            selectedTime = state.selectedTime,
            onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
        )
        IconWithText(
            painter = painterResource(R.drawable.flash_on_24px),
            text = stringResource(R.string.flash_text)
        )
        RowTimeButton(
            times = listOf(
                stringResource(R.string.three_minute_text),
                stringResource(R.string.three_minute_plus_two_text),
                stringResource(R.string.five_minute_text)
            ),
            selectedTime = state.selectedTime,
            onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
        )
        AnimatedVisibility(
            visible = state.isMoreSetting,
            enter = fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                    expandVertically(animationSpec = tween(500, easing = FastOutSlowInEasing)),
            exit = fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                    shrinkVertically(animationSpec = tween(500, easing = FastOutSlowInEasing))
        ) {
            RowTimeButton(
                text1 = stringResource(R.string.five_minute_plus_five_text),
                text2 = stringResource(R.string.five_min_plus_two),
                selectedTime = state.selectedTime,
                onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
            )
        }
        IconWithText(
            painter = painterResource(R.drawable.timer_24px),
            text = stringResource(R.string.fast_text)
        )
        RowTimeButton(
            times = listOf(
                stringResource(R.string.ten_minute_text),
                stringResource(R.string.fifteen_minute_plus_ten),
                stringResource(R.string.thirty_minute)
            ),
            selectedTime = state.selectedTime,
            onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
        )
        AnimatedVisibility(
            visible = state.isMoreSetting,
            enter = fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                    expandVertically(animationSpec = tween(500, easing = FastOutSlowInEasing)),
            exit = fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                    shrinkVertically(animationSpec = tween(500, easing = FastOutSlowInEasing))
        ) {
            RowTimeButton(
                times = listOf(
                    stringResource(R.string.ten_min_plus_5),
                    stringResource(R.string.twenty_min),
                    stringResource(R.string.sixty_min)
                ),
                selectedTime = state.selectedTime,
                onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
            )
        }
        IconWithText(
            painter = painterResource(R.drawable.wb_sunny_24px),
            text = stringResource(R.string.daily_text)
        )
        RowTimeButton(
            times = listOf(
                stringResource(R.string.one_day),
                stringResource(R.string.three_days_text),
                stringResource(R.string.seven_days_text)
            ),
            selectedTime = state.selectedTime,
            onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
        )
        AnimatedVisibility(
            visible = state.isMoreSetting,
            enter = fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                    expandVertically(animationSpec = tween(500, easing = FastOutSlowInEasing)),
            exit = fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing)) +
                    shrinkVertically(animationSpec = tween(500, easing = FastOutSlowInEasing))
        ) {
            Column {
                RowTimeButton(
                    times = listOf(
                        stringResource(R.string.two_days_text),
                        stringResource(R.string.five_days_text),
                        stringResource(R.string.fourteen_days)
                    ),
                    selectedTime = state.selectedTime,
                    onClick = { onAction(ChangeTimeAction.ClickedButton(it)) }
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconWithText(
                        painter = painterResource(R.drawable.build_24px),
                        text = stringResource(R.string.custom_text)
                    )
                    CustomTimeSelect()
                }
            }
        }
        TextButton(
            onClick = { onAction(ChangeTimeAction.ToggleShowMore) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(if (state.isMoreSetting) R.string.fewer_time_controls_text else R.string.more_time_controls_text),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelMedium
                )
                Icon(
                    painter = painterResource(
                        if (state.isMoreSetting) R.drawable.keyboard_arrow_up_24px
                        else R.drawable.keyboard_arrow_down_24px
                    ),
                    contentDescription = "icon"
                )
            }
        }
    }
}