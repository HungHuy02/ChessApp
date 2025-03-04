package com.huy.chess.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//@Composable
//fun RowTimeButton(
//    modifier: Modifier = Modifier,
//    text1: String,
//    text2: String,
//    text3: String,
//    selectedTime: String,
//    onClick: (String) -> Unit
//) {
//    Row(modifier = modifier) {
//        TimeButton(
//            text = text1,
//            modifier = Modifier.weight(1f),
//            isSelected = selectedTime == text1,
//            onClick = { onClick(it) }
//        )
//        Spacer(modifier = Modifier.size(8.dp))
//        TimeButton(
//            text = text2,
//            modifier = Modifier.weight(1f),
//            isSelected = selectedTime == text2,
//            onClick = { onClick(it) }
//        )
//        Spacer(modifier = Modifier.size(8.dp))
//        TimeButton(
//            text = text3,
//            modifier = Modifier.weight(1f),
//            isSelected = selectedTime == text3,
//            onClick = { onClick(it) }
//        )
//    }
//}

@Composable
fun RowTimeButton(
    modifier: Modifier = Modifier,
    times: List<String>,
    selectedTime: String,
    onClick: (String) -> Unit
) {
    Row(modifier = modifier) {
        times.forEachIndexed { index, time ->
            TimeButton(
                text = time,
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
    text1: String,
    text2: String,
    selectedTime: String,
    onClick: (String) -> Unit
) {
    Row(modifier = modifier) {
        TimeButton(
            text = text1,
            modifier = Modifier.weight(1f),
            isSelected = selectedTime == text1,
            onClick = { onClick(it) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        TimeButton(
            text = text2,
            modifier = Modifier.weight(1f),
            isSelected = selectedTime == text2,
            onClick = { onClick(it) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        Spacer(modifier = Modifier.weight(1f))
    }
}