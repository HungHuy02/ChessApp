package com.huy.chess.ui.moreoptions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.moreoptions.composables.OptionItem
import com.huy.chess.R
import com.huy.chess.ui.component.BaseScreen

@Composable
fun MoreOptionsScreen() {
    BaseScreen() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            OptionItem(
                painter = painterResource(R.drawable.signal_cellular_alt_24px),
                text = stringResource(R.string.stats_text)
            )
            OptionItem(
                painter = painterResource(R.drawable.person_24px),
                text = stringResource(R.string.profile_text)
            )
            OptionItem(
                painter = painterResource(R.drawable.friends),
                text = stringResource(R.string.setting_text)
            )
            OptionItem(
                painter = painterResource(R.drawable.mail_24px),
                text = stringResource(R.string.message_text)
            )
            OptionItem(
                painter = painterResource(R.drawable.settings_24px),
                text = stringResource(R.string.setting_text)
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    MoreOptionsScreen()
}