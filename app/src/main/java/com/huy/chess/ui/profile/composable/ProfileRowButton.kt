package com.huy.chess.ui.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.theme.ChessSansFontFamily

@Composable
fun ProfileRowButton(
    modifier: Modifier = Modifier,
    text: String,
    number: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onClick()
            }.padding(16.dp)
    ) {
        Text(
            text = text,
            fontFamily = ChessSansFontFamily
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = number.toString()
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            painter = painterResource(R.drawable.chevron_right_24px),
            contentDescription = "icon"
        )
    }
}
