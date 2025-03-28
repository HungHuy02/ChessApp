package com.huy.chess.ui.settings.composable

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.settings.SettingsAction
import com.huy.chess.utils.Utils

data class SettingsRow(
    val imageBitmap: ImageBitmap,
    val label: String,
    val action: SettingsAction
)

@Composable
fun SettingsRow(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Icon(
            bitmap = imageBitmap,
            contentDescription = "icon"
        )
        Spacer(Modifier.width(24.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

fun settingsRowList(context: Context) : List<SettingsRow> {
    return listOf(
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.profile_text), SettingsAction.ClickedProfile),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.account_text), SettingsAction.CLickedAccount),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.notifications_text), SettingsAction.ClickedNotifications),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.home_text), SettingsAction.ClickedHome),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.board_text), SettingsAction.ClickedBoard),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.play_text), SettingsAction.ClickedPlay),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.puzzle_text), SettingsAction.ClickedPuzzles),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.analysis_text), SettingsAction.ClickedAnalysis),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.privacy_settings_text), SettingsAction.ClickedPrivacySettings),
        SettingsRow(Utils.loadImageBimap(context, R.drawable.close_24px), context.getString(R.string.log_out_text), SettingsAction.ClickedLogOut),
    )
}