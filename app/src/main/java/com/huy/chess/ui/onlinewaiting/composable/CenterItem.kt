package com.huy.chess.ui.onlinewaiting.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.theme.Gray400
import com.huy.chess.utils.enums.TimeType
import com.huy.chess.utils.toPair

@Composable
fun CenterItem(
    modifier: Modifier = Modifier,
    timeType: TimeType
) {
    val pair = timeType.toPair()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.small)
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(pair.first),
            contentDescription = "icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) {
                    appendLine(stringResource(pair.second))
                }
                withStyle(SpanStyle(color = Gray400)) {
                    append(stringResource(R.string.comming_soon_text))
                }
            },
            textAlign = TextAlign.Center
        )

    }
}