package com.huy.chess.ui.onlinewaiting.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.R

@Composable
fun OnlineWaitingBottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
            .clickable {
                onClick()
            }.padding(horizontal = 4.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily, fontSize = 24.sp)) {
                    appendLine("\u0042")
                }
                withStyle(SpanStyle()) {
                    append(stringResource(R.string.cancel_text))
                }
            },
            textAlign = TextAlign.Center
        )
    }
}