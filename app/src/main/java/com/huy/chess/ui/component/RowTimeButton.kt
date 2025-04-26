package com.huy.chess.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.huy.chess.utils.enums.TimeType

@Composable
fun RowTimeButton(
    modifier: Modifier = Modifier,
    times: List<TimeType>,
    selectedTime: TimeType,
    onClick: (TimeType) -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 8.dp)
    ) {
        times.forEachIndexed { index, time ->
            TimeButton(
                timeType = time,
                modifier = Modifier.weight(1f),
                isSelected = selectedTime == time,
                onClick = { onClick(it) }
            )

            if (index < times.size - 1) {
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun RowTimeButton(
    modifier: Modifier = Modifier,
    timeType1: TimeType,
    timeType2: TimeType,
    selectedTime: TimeType,
    onClick: (TimeType) -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 8.dp)
    ) {
        TimeButton(
            timeType = timeType1,
            modifier = Modifier.weight(1f),
            isSelected = selectedTime == timeType1,
            onClick = { onClick(it) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        TimeButton(
            timeType = timeType2,
            modifier = Modifier.weight(1f),
            isSelected = selectedTime == timeType2,
            onClick = { onClick(it) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        Spacer(modifier = Modifier.weight(1f))
    }
}