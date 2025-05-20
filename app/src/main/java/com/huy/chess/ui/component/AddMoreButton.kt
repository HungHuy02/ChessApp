package com.huy.chess.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.ui.theme.ChessSansFontFamily

@Composable
fun AddMoreButton(
    modifier: Modifier = Modifier,
    text: String,
    isShow: Boolean = false,
    onClick: () -> Unit
) {
    val icon = if(isShow) "\u003E" else "\u003C"
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontFamily = ChessSansFontFamily, fontSize = 12.sp)) {
                append(text)
            }
            withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                append(" $icon")
            }
        },
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 12.dp)

    )
}
