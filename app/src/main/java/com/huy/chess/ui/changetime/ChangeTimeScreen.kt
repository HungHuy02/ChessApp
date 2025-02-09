package com.huy.chess.ui.changetime

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.changetime.composables.CustomTimeSelect
import com.huy.chess.ui.changetime.composables.IconWithText
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.component.RowTimeButton

@Composable
fun ChangeTimeScreen() {
    var showMore by remember {
        mutableStateOf(false)
    }

    BaseScreen(
        title = stringResource(R.string.time_text),
        showBackIcon = true
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IconWithText(
                painter = painterResource(R.drawable.bullet),
                text = stringResource(R.string.super_flash_text)
            )
            RowTimeButton(
                text1 = stringResource(R.string.one_minute_text),
                text2 = stringResource(R.string.one_minute_plus_one_text),
                text3 = stringResource(R.string.two_minute_plus_one_text)
            )
            IconWithText(
                painter = painterResource(R.drawable.flash_on_24px),
                text = stringResource(R.string.flash_text)
            )
            RowTimeButton(
                text1 = stringResource(R.string.three_minute_text),
                text2 = stringResource(R.string.three_minute_plus_two_text),
                text3 = stringResource(R.string.five_minute_text)
            )
            AnimatedVisibility(showMore) {
                RowTimeButton(
                    text1 = stringResource(R.string.five_minute_plus_five_text),
                    text2 = stringResource(R.string.five_min_plus_two)
                )
            }
            IconWithText(
                painter = painterResource(R.drawable.timer_24px),
                text = stringResource(R.string.fast_text)
            )
            RowTimeButton(
                text1 = stringResource(R.string.ten_minute_text),
                text2 = stringResource(R.string.fifteen_minute_plus_ten),
                text3 = stringResource(R.string.thirty_minute)
            )
            AnimatedVisibility(showMore) {
                RowTimeButton(
                    text1 = stringResource(R.string.ten_min_plus_5),
                    text2 = stringResource(R.string.twenty_min),
                    text3 = stringResource(R.string.sixty_min)
                )
            }
            IconWithText(
                painter = painterResource(R.drawable.wb_sunny_24px),
                text = stringResource(R.string.daily_text)
            )
            RowTimeButton(
                text1 = stringResource(R.string.one_day),
                text2 = stringResource(R.string.three_days_text),
                text3 = stringResource(R.string.seven_days_text)
            )
            AnimatedVisibility(showMore) {
                RowTimeButton(
                    text1 = stringResource(R.string.two_days_text),
                    text2 = stringResource(R.string.five_days_text),
                    text3 = stringResource(R.string.fourteen_days)
                )
            }
            AnimatedVisibility(showMore) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconWithText(
                        painter = painterResource(R.drawable.build_24px),
                        text = stringResource(R.string.custom_text)
                    )
                    CustomTimeSelect()
                }
            }
            TextButton(
                onClick = {
                    showMore = !showMore
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(if (showMore) R.string.fewer_time_controls_text else R.string.more_time_controls_text),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Icon(
                        painter = painterResource(
                            if (showMore) R.drawable.keyboard_arrow_up_24px
                            else R.drawable.keyboard_arrow_down_24px
                        ),
                        contentDescription = "icon"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChangeTimeScreen()
}