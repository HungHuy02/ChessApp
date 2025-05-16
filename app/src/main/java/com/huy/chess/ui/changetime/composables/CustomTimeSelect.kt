package com.huy.chess.ui.changetime.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.utils.Constants
import com.huy.chess.R

@Composable
fun CustomTimeSelect(
    modifier: Modifier = Modifier,
    initTime: Int,
    plusTime: Int
) {
    Card(
        onClick = {},
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            TimeSlider(
                maxSelect = Constants.MAX_TIME_SELECT,
                time = initTime.toFloat(),
                isInitialTime = true
            )
            TimeSlider(
                maxSelect = Constants.MAX_BONUS_TIME_SELECT,
                time = plusTime.toFloat(),
                isInitialTime = false
            )
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.select_text),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}