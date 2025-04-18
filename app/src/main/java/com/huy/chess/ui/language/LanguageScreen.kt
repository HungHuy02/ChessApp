package com.huy.chess.ui.language

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.language.composable.LanguageRow
import com.huy.chess.viewmodel.LanguageViewModel

@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                LanguageEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: LanguageState,
    onAction: (LanguageAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChessTopAppBar(
            onClickBack = { onAction(LanguageAction.ClickedBack) },
            title = stringResource(R.string.language_text)
        )
        LanguageRow(
            text = stringResource(R.string.display_content_in_english_text),
            checked = state.isDisplayInEnglishWhenNotAvailable
        ) {
            onAction(LanguageAction.ToggleDisplayInEnglish)
        }

        LanguageRow(
            text = stringResource(R.string.force_english_text),
            checked = state.isForceLanguage
        ) {
            onAction(LanguageAction.ToggleForceEnglish)
        }

        AppButton(
            text = stringResource(R.string.select_language_text),
            iconPosition = IconPosition.NONE,
            onClick = { onAction(LanguageAction.ClickedSelectLanguage) },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}