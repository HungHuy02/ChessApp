package com.huy.chess.ui.dialog.endgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@Composable
fun EndGameDialog(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EndGameDialogHeader()
        EndGameImageAndResult()
        Buttons()
    }
}

@Composable
fun EndGameDialogHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "\u0042",
            fontSize = 24.sp,
            fontFamily = ChessGlyphFontFamily
        )
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    appendLine(stringResource(R.string.you_won_text))
                }
                withStyle(SpanStyle()) {
                    append(stringResource(R.string.checkmate_text))
                }
            },
            textAlign = TextAlign.Center
        )
        Text(
            text = "\u00A5",
            fontSize = 24.sp,
            fontFamily = ChessGlyphFontFamily
        )
    }
}

@Composable
fun EndGameImageAndResult(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (image, opponentImage, name, opponentName, icon, result ) = createRefs()
        AsyncImage(
            model = R.drawable.noavatar,
            contentDescription = "image",
            modifier = Modifier
                .size(88.dp)
                .constrainAs(image) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = "test",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(image.start)
                top.linkTo(image.bottom, margin = 8.dp)
                end.linkTo(image.end)
            }
        )

        Icon(
            painter = painterResource(R.drawable.rapid),
            contentDescription = "rapid icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .constrainAs(icon) {
                start.linkTo(image.end)
                top.linkTo(image.top)
                end.linkTo(opponentImage.start)
                bottom.linkTo(image.bottom)
            }
        )

        Text(
            text = "1-0",
            modifier = Modifier.constrainAs(result) {
                start.linkTo(image.end)
                end.linkTo(opponentImage.start)
                bottom.linkTo(parent.bottom)
            }
        )

        AsyncImage(
            model = R.drawable.noavatar,
            contentDescription = "image",
            modifier = Modifier
                .size(88.dp)
                .constrainAs(opponentImage) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = "test",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(opponentName) {
                start.linkTo(opponentImage.start)
                top.linkTo(opponentImage.bottom, margin = 8.dp)
                end.linkTo(opponentImage.end)
            }
        )

    }
}

@Composable
fun Buttons() {
    AppButton(
        onClick = {},
        text = stringResource(R.string.review_game_text),
        iconPosition = IconPosition.NONE,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        AppButton(
            onClick = {},
            text = stringResource(R.string.rematch_text),
            iconPosition = IconPosition.NONE,
            textStyle = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(16.dp))
        AppButton(
            onClick = {},
            text = stringResource(R.string.time_new_text, "10 min "),
            iconPosition = IconPosition.NONE,
            textStyle = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
    }
}