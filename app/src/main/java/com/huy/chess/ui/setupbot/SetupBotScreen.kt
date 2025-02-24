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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.changetime.composables.TimeButton
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.RowItem
import com.huy.chess.ui.component.RowItemWithSwitch
import com.huy.chess.ui.component.RowTimeButton
import com.huy.chess.ui.setupbot.composables.IconWithText
import com.huy.chess.ui.setupbot.composables.LevelSelect
import com.huy.chess.ui.setupbot.composables.PieceSelect

@Composable
fun SetupBotScreen() {
    var showTimeControl by remember {
        mutableStateOf(false)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        IconWithText()
        Text(
            text = stringResource(R.string.play_with_color_text),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        PieceSelect()
        Text(
            text = stringResource(R.string.level_text),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        LevelSelect()
        RowItem(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.time_control_text),
            text = stringResource(R.string.no_time_text),
            onClick = {
                showTimeControl = !showTimeControl
            }
        )
        AnimatedVisibility(showTimeControl) {
            Column {
                RowTimeButton(
                    text1 = stringResource(R.string.thirty_minute),
                    text2 = stringResource(R.string.fifteen_minute_plus_ten),
                    text3 = stringResource(R.string.ten_minute_text)
                )
                RowTimeButton(
                    text1 = stringResource(R.string.five_minute_plus_five_text),
                    text2 = stringResource(R.string.three_minute_plus_two_text),
                    text3 = stringResource(R.string.two_minute_plus_one_text)
                )
                RowTimeButton(
                    text1 = stringResource(R.string.five_minute_text),
                    text2 = stringResource(R.string.three_minute_text),
                    text3 = stringResource(R.string.one_minute_text)
                )
                TimeButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.no_time_text)
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

@Preview
@Composable
private fun Preview() {
    SetupBotScreen()
}