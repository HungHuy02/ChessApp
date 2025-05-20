package com.huy.chess.ui.changetime.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.theme.Gray600
import com.huy.chess.ui.theme.TransparentWhite10
import com.huy.chess.utils.Constants

@Composable
fun CustomTimeSelect(
    modifier: Modifier = Modifier,
    initTime: Int,
    plusTime: Int
) {
    Card(
        onClick = {},
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Gray600
        ),
        modifier = modifier.wrapContentSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = TransparentWhite10
                ),
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