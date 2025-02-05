package com.huy.chess.ui.changetime.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun TimeSlider(
    modifier: Modifier = Modifier,
    maxSelect: Float,
    isInitialTime: Boolean
) {
    var sliderPosition by remember {
        mutableFloatStateOf(0f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(stringResource(if(isInitialTime) R.string.initial_time_text else R.string.bonus_time_text))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(
                        stringResource(
                            if (isInitialTime) R.string.select_initial_time_text else R.string.select_bonus_time,
                            sliderPosition.toInt()
                        )
                    )
                }
            },
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.min_select_text),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
            Slider(
                value = sliderPosition,
                valueRange = 0f..maxSelect,
                onValueChange = { sliderPosition = it },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = maxSelect.toInt().toString(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }


}