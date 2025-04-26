package com.huy.chess.ui.setuptwopeople.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.huy.chess.R

@Composable
fun NameArea(
    modifier: Modifier = Modifier,
    whiteName: String,
    onWhiteNameChange: (String) -> Unit,
    blackName: String,
    onBlackNameChange: (String) -> Unit,
    onClickChange: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (rowWhite, rowBlack, icon) = createRefs()
        RowItemWithEditText(
            label = stringResource(R.string.white_text),
            text = whiteName,
            onValueChange = onWhiteNameChange,
            modifier = Modifier
                .constrainAs(rowWhite) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        RowItemWithEditText(
            label = stringResource(R.string.black_text),
            text = blackName,
            onValueChange = onBlackNameChange,
            modifier = Modifier
                .constrainAs(rowBlack) {
                    top.linkTo(rowWhite.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Icon(
            painter = painterResource(R.drawable.swap_vertical_circle_24px),
            contentDescription = "swap icon",
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickChange()
                }
        )
    }
}