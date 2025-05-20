package com.huy.chess.ui.onlinewaiting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.PlayerArea
import com.huy.chess.ui.onlinewaiting.composable.CenterItem
import com.huy.chess.ui.onlinewaiting.composable.OnlineWaitingBottomBar
import com.huy.chess.utils.enums.TimeType
import com.huy.chess.viewmodel.OnlineWaitingViewModel

@Composable
fun OnlineWaitingScreen(
    viewModel: OnlineWaitingViewModel = hiltViewModel(),
    navigatePlayOnline: () -> Unit,
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                OnlineWaitingEffect.NavigatePlayOnline -> navigatePlayOnline()
                OnlineWaitingEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (OnlineWaitingAction) -> Unit
) {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, topPlayer, board, center, bottomPlayer, bottomBar) = createRefs()
        ChessTopAppBar(
            icon = painterResource(R.drawable.logo),
            iconHeight = 28.dp,
            onAction = {},
            onClickBack = { onAction(OnlineWaitingAction.ClickedBack) },
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
            }
        )

        PlayerArea(
            name = stringResource(R.string.search_text),
            side = true,
            modifier = Modifier.constrainAs(topPlayer) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(board.top)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(R.drawable.standardboard),
            contentDescription = "board",
            alpha = 0.2f,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().constrainAs(board) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )

        CenterItem(
            timeType = TimeType.TEN_MINUTES,
            modifier = Modifier.constrainAs(center) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )

        PlayerArea(
            name = "",
            side = true,
            modifier = Modifier.constrainAs(bottomPlayer) {
                top.linkTo(board.bottom)
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(bottomBar.top)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        OnlineWaitingBottomBar(
            onClick = { onAction(OnlineWaitingAction.ClickedCancel) },
            modifier = Modifier.constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom)
            }
        )
    }
}