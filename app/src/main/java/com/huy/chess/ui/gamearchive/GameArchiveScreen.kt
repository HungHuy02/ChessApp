package com.huy.chess.ui.gamearchive

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.VerticalHistoryList
import com.huy.chess.viewmodel.GameArchiveViewModel

@Composable
fun GameArchiveScreen(
    viewModel: GameArchiveViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                GameArchiveEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(onAction: (GameArchiveAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ChessTopAppBar(
            onClickBack = {
                onAction(GameArchiveAction.ClickedBack)
            },
            title = stringResource(R.string.game_archive_text),
            onAction = {
                Icon(
                    painter = painterResource(R.drawable.search_24px),
                    contentDescription = "search icon",
                    modifier = Modifier
                        .clickable {
                            onAction(GameArchiveAction.ClickedSearch)
                        }
                )
            }
        )
        VerticalHistoryList()
    }
}