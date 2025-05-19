package com.huy.chess.ui.newgame.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@Composable
fun AddMoreButton(
    modifier: Modifier = Modifier,
    isShow: Boolean = false,
    onClick: () -> Unit
) {
    val icon = if(isShow) "\u003E" else "\u003C"
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle()) {
                append(stringResource(R.string.more_text))
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
