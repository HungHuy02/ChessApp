package com.huy.chess.ui.play.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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

@Composable
fun PlayScreenBottomBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PlayScreenBottomBarItem.itemList.forEach {
            PlayScreenBottomBarItem(
                painter = painterResource(it.icon),
                text = stringResource(it.label),
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
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
    @StringRes val label: Int
) {
    data object Options : PlayScreenBottomBarItem(
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text
    )

    data object Add : PlayScreenBottomBarItem(
        icon = R.drawable.add_24px,
        label = R.string.more_options_text
    )

    data object Back : PlayScreenBottomBarItem(
        icon = R.drawable.chevron_left_24px,
        label = R.string.back_text
    )

    data object Forward : PlayScreenBottomBarItem(
        icon = R.drawable.chevron_right_24px,
        label = R.string.forward_text
    )

    companion object {
        val itemList = listOf(Options, Add, Back, Forward)
    }
}