package com.huy.chess.ui.newgame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.newgame.composables.SameButton
import com.huy.chess.ui.newgame.composables.TimeButton

@Composable
fun NewGameScreen(
    navigateToChangeTime: () -> Unit,
    navigateToSetupBot: () -> Unit,
    navigateToSetupTwoPeople: () -> Unit
) {
    var showMore by remember {
        mutableStateOf(false)
    }

    BaseScreen(
        title = stringResource(R.string.new_game_text),
        showBackIcon = true
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimeButton(modifier = Modifier.fillMaxWidth()) {
                navigateToChangeTime()
            }
            AppButton(
                onClick = {},
                text = stringResource(R.string.new_game_text),
                textStyle = MaterialTheme.typography.titleLarge,
                textColor = MaterialTheme.colorScheme.onPrimary,
                iconPosition = IconPosition.NONE,
                modifier = Modifier.fillMaxWidth()
            )
            SameButton(
                onClick = {},
                text = stringResource(R.string.play_with_friend_text),
                painter = painterResource(R.drawable.handshake_8c90be47)
            )
            SameButton(
                onClick = navigateToSetupBot,
                text = stringResource(R.string.play_with_bot_text),
                painter = painterResource(R.drawable.cute_bot_32735490)
            )
            TextButton(
                onClick = {
                    showMore = !showMore
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.more_text),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displaySmall
                )
                Icon(
                    painter = painterResource(R.drawable.keyboard_arrow_down_24px),
                    contentDescription = "icon"
                )
            }
            AnimatedVisibility(showMore) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SameButton(
                        onClick = {},
                        text = stringResource(R.string.custom_game_text),
                        painter = painterResource(R.drawable.tune_24px)
                    )
                    SameButton(
                        onClick = navigateToSetupTwoPeople,
                        text = stringResource(R.string.pass_and_play_text),
                        painter = painterResource(R.drawable.friends)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NewGameScreen({},{},{})
}