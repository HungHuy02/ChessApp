package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    painter: Painter? = null,
    iconPosition: IconPosition
) {
    CompositionLocalProvider (LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
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
                    style = textStyle
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
                            text = text,
                            style = textStyle
                        )
                    }
                }

                IconPosition.NEXT_TO_TEXT -> {
                    Icon(
                        painter = painter!!,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(20.dp)
                    )
                    Text(
                        text = text,
                        style = textStyle
                    )
                }
            }
        }
    }


}

enum class IconPosition {
    NONE,
    START,
    NEXT_TO_TEXT
}

@Preview
@Composable
private fun Preview() {
    AppButton(
        onClick = {},
        text = "test",
        iconPosition = IconPosition.NONE,
        modifier = Modifier.fillMaxWidth()
    )
}
