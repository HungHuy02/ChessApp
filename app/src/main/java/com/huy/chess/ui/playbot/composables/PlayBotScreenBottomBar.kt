package com.huy.chess.ui.playbot.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.ui.playbot.PlayBotAction

@Composable
fun PlayBotScreenBottomBar(
    modifier: Modifier = Modifier,
    onClick: (PlayBotAction) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PlayScreenBottomBarItem.itemList.forEach {
            PlayScreenBottomBarItem(
                painter = painterResource(it.icon),
                text = stringResource(it.label),
                onClick = { onClick(it.playAction) },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun PlayScreenBottomBarItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        Icon(
            painter = painter,
            contentDescription = "icon"
        )
        Text(
            text = text
        )
    }
}

sealed class PlayScreenBottomBarItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val playAction: PlayBotAction
) {
    data object Options : PlayScreenBottomBarItem(
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text,
        playAction = PlayBotAction.ClickedMore
    )

    data object Add : PlayScreenBottomBarItem(
        icon = R.drawable.add_24px,
        label = R.string.more_options_text,
        playAction = PlayBotAction.ClickedAdd
    )

    data object Back : PlayScreenBottomBarItem(
        icon = R.drawable.chevron_left_24px,
        label = R.string.back_text,
        playAction = PlayBotAction.ClickedBack
    )

    data object Forward : PlayScreenBottomBarItem(
        icon = R.drawable.chevron_right_24px,
        label = R.string.forward_text,
        playAction = PlayBotAction.ClickedForward
    )

    companion object {
        val itemList = listOf(Options, Add, Back, Forward)
    }
}