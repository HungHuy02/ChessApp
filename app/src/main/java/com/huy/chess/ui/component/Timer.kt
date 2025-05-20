package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.theme.Gray400
import com.huy.chess.utils.Formatters
import com.huy.chess.R

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    time: Long,
    isWhite: Boolean,
    isRun: Boolean = false
) {
    Row(
        modifier = modifier
            .background(if(isWhite) Color.Black else Gray400)
            .padding(vertical = 2.dp)
    ) {
        if(isRun) {
            Icon(
                painter = painterResource(R.drawable.timer),
                tint = Color.White,
                contentDescription = "icon"
            )
            Spacer(Modifier.width(8.dp))
        }
        Text(
            text = Formatters.formatSmartTime(time),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }

}