package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun ErrorAlert(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min)
            .background(color = MaterialTheme.colorScheme.errorContainer, shape = MaterialTheme.shapes.small)
    ) {
        Box(Modifier.width(4.dp).fillMaxHeight().background(color = Color.Red, shape = RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp)))
        Icon(
            painter = painterResource(R.drawable.priority_high),
            contentDescription = "warning icon",
            tint = Color.Unspecified,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}