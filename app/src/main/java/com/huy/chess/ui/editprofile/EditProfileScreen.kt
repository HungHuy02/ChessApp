package com.huy.chess.ui.editprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.editprofile.composable.TextWithTextField
import com.huy.chess.viewmodel.EditProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigateLanguage: () -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                EditProfileEffect.NavigateLanguage -> navigateLanguage()
                EditProfileEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: EditProfileState,
    onAction: (EditProfileAction) -> Unit
) {
    Column {
        ChessTopAppBar(
            onClick = { onAction(EditProfileAction.ClickedLeft) },
            title = stringResource(R.string.language_text),
            isVerify = state.isVerify,
            onClickDone = { onAction(EditProfileAction.ClickedDone) }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onAction(EditProfileAction.ClickedPicture)
                } .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.profile_picture_text)
            )
            AsyncImage(
                model = R.drawable.noavatar,
                contentDescription = "image"
            )
            TextWithTextField(
                text = stringResource(R.string.flair_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedFlair) }
            )
            TextWithTextField(
                text = stringResource(R.string.stats_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedStatus) }
            )
            TextWithTextField(
                text = stringResource(R.string.username_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedUserName) }
            )
            TextWithTextField(
                text = stringResource(R.string.first_name_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedFirstName) }
            )
            TextWithTextField(
                text = stringResource(R.string.last_name_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedLastName) }
            )
            TextWithTextField(
                text = stringResource(R.string.country_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedCountry) }
            )
            TextWithTextField(
                text = stringResource(R.string.location_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedLocation) }
            )
            TextWithTextField(
                text = stringResource(R.string.language_text),
                value = "",
                onValueChange = {},
                onClick = { onAction(EditProfileAction.ClickedLanguage) }
            )
        }

    }
}
