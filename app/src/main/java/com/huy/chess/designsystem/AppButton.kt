package com.huy.chess.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    fontSize: TextUnit = 40.sp,
    textColor: Color,
    backgroundColor: Color = Color.Black,
    painter: Painter? = null,
    iconPosition: IconPosition
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = modifier
            .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(top = 0.8.dp)
            .background(Color.Blue, shape = RoundedCornerShape(10.dp))
            .padding(bottom = 1.dp)
    ) {
        when (iconPosition) {
            IconPosition.NONE -> Text(
                text = text,
                fontSize = fontSize
            )

            IconPosition.START -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painter!!,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterStart)
                    )
                    Text(
                        text = "text",
                        fontSize = fontSize,
                        modifier = Modifier
                    )
                }
            }

            IconPosition.NEXT_TO_TEXT -> {
                Icon(
                    painter = painter!!,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(20.dp)
                )
                Text(
                    text = "text",
                    fontSize = fontSize,
                    modifier = Modifier
                )
            }
        }
    }
}

enum class IconPosition {
    NONE,
    START,
    NEXT_TO_TEXT
}
