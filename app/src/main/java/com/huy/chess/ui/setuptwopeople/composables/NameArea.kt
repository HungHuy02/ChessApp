package com.huy.chess.ui.setuptwopeople.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.huy.chess.R

@Composable
fun NameArea(modifier: Modifier = Modifier) {
    val stateWhite = rememberTextFieldState()
    val stateBlack = rememberTextFieldState()
    ConstraintLayout(modifier = modifier) {
        val (rowWhite, rowBlack, icon) = createRefs()
        RowItemWithEditText(
            label = stringResource(R.string.white_text),
            state = stateWhite,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(rowWhite) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        RowItemWithEditText(
            label = stringResource(R.string.black_text),
            state = stateBlack,
            modifier = Modifier
                .fillMaxWidth()
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
                .clickable {

                }
        )
    }
}