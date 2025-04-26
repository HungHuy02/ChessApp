package com.huy.chess.ui.setuptwopeople.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.component.RowTimeButton
import com.huy.chess.ui.component.TimeButton
import com.huy.chess.utils.enums.TimeType

@Composable
fun SetupTwoTimeControl(
    modifier: Modifier = Modifier,
    selectedTime: TimeType,
    onClick: (TimeType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        RowTimeButton(
            times = listOf(
                TimeType.THIRTY_MINUTES,
                TimeType.FIFTEEN_MINUTES_PLUS_TEN,
                TimeType.TEN_MINUTES
            ),
            selectedTime = selectedTime,
            onClick = { onClick(it) }
        )
        RowTimeButton(
            times = listOf(
                TimeType.FIVE_MINUTES_PLUS_FIVE,
                TimeType.THREE_MINUTES_PLUS_TWO,
                TimeType.TWO_MINUTES_PLUS_ONE
            ),
            selectedTime = selectedTime,
            onClick = { onClick(it) }
        )
        RowTimeButton(
            times = listOf(
                TimeType.FIVE_MINUTES,
                TimeType.THREE_MINUTES,
                TimeType.ONE_MINUTE
            ),
            selectedTime = selectedTime,
            onClick = { onClick(it) }
        )
        TimeButton(
            modifier = Modifier.fillMaxWidth(),
            timeType = TimeType.UNLIMITED,
            isSelected = selectedTime == TimeType.UNLIMITED,
            onClick = { onClick(it) }
        )
    }
}